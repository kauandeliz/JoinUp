package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Organizador;
import view.OrganizadorView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizadorController {

    private static final String STORAGE_FILE = "organizadores.ser";

    private OrganizadorView view;
    private List<Organizador> organizadores;

    public OrganizadorController(OrganizadorView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.organizadores = carregarOrganizadores();

        this.view.atualizarTabela(organizadores);

        this.view.getBotaoSalvar().setOnAction(event -> salvarOrganizador());
        this.view.getBotaoEditar().setOnAction(event -> editarOrganizador());
        this.view.getBotaoExcluir().setOnAction(event -> excluirOrganizador());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        this.view.getTabelaOrganizadores().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarOrganizador() {
        try {
            String nome = view.getNome();
            String email = view.getEmail();
            String telefone = view.getTelefone();
            String empresa = view.getEmpresa();

            if (!validarCampos(nome, email, telefone)) {
                return;
            }

            Organizador novoOrganizador = new Organizador(nome, email, telefone, empresa);
            organizadores.add(novoOrganizador);
            System.out.println("Organizador criado: " + novoOrganizador);

            persistirOrganizadores();
            view.atualizarTabela(organizadores);
            view.limparSelecao();
            mostrarSucesso("Organizador salvo com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar organizador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editarOrganizador() {
        try {
            Organizador selecionado = view.getOrganizadorSelecionado();
            if (selecionado == null) {
                mostrarErro("Selecione um organizador para editar.");
                return;
            }

            String nome = view.getNome();
            String email = view.getEmail();
            String telefone = view.getTelefone();
            String empresa = view.getEmpresa();

            if (!validarCampos(nome, email, telefone)) {
                return;
            }

            selecionado.setNome(nome);
            selecionado.setEmail(email);
            selecionado.setTelefone(telefone);
            selecionado.setEmpresa(empresa);
            System.out.println("Organizador atualizado: " + selecionado);

            persistirOrganizadores();
            view.atualizarTabela(organizadores);
            view.limparSelecao();
            mostrarSucesso("Organizador editado com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao editar organizador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos(String nome, String email, String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            mostrarErro("Nome do organizador e obrigatorio.");
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            mostrarErro("Email do organizador e obrigatorio.");
            return false;
        }

        if (telefone == null || telefone.trim().isEmpty()) {
            mostrarErro("Telefone do organizador e obrigatorio.");
            return false;
        }

        return true;
    }

    private void excluirOrganizador() {
        Organizador selecionado = view.getOrganizadorSelecionado();
        if (selecionado == null) {
            System.out.println("Selecione um organizador para excluir.");
            return;
        }

        organizadores.remove(selecionado);
        persistirOrganizadores();
        view.atualizarTabela(organizadores);
        view.limparSelecao();
        System.out.println("Organizador excluido: " + selecionado);
    }

    private void persistirOrganizadores() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            if (organizadores == null) {
                System.err.println("Erro: Lista de organizadores e nula.");
                return;
            }

            oos.writeObject(organizadores);
            System.out.println("Organizadores salvos com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de organizadores nao encontrado - " + STORAGE_FILE);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao salvar organizadores: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar organizadores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Organizador> carregarOrganizadores() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object objeto = ois.readObject();

            if (objeto == null) {
                System.out.println("Arquivo existe mas esta vazio. Iniciando lista vazia.");
                return new ArrayList<>();
            }

            if (!(objeto instanceof List)) {
                System.err.println("Erro: Arquivo nao contem uma lista valida.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Organizador> lista = (List<Organizador>) objeto;

            if (lista.isEmpty()) {
                System.out.println("Nenhum organizador carregado.");
                return lista;
            }

            ajustarNextId(lista);
            System.out.println("Organizadores carregados: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de organizadores nao existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de organizadores esta corrompido (vazio). Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de organizador nao encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de organizadores: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar organizadores: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Organizador> lista) {
        int maiorId = 0;
        for (Organizador organizador : lista) {
            if (organizador.getId() > maiorId) {
                maiorId = organizador.getId();
            }
        }
        Organizador.setNextId(maiorId + 1);
    }

    private void mostrarErro(String mensagem) {
        System.err.println("[ERRO] " + mensagem);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Operacao nao pode ser concluida");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarSucesso(String mensagem) {
        System.out.println("[SUCESSO] " + mensagem);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
