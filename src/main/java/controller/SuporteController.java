package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Suporte;
import view.SuporteView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SuporteController {

    private static final String STORAGE_FILE = "suportes.ser";

    private SuporteView view;
    private List<Suporte> suportes;

    public SuporteController(SuporteView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.suportes = carregarSuportes();

        this.view.atualizarTabela(suportes);

        this.view.getBotaoSalvar().setOnAction(event -> salvarSuporte());
        this.view.getBotaoEditar().setOnAction(event -> editarSuporte());
        this.view.getBotaoExcluir().setOnAction(event -> excluirSuporte());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        this.view.getTabelaSuportes().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarSuporte() {
        try {
            String nome = view.getNome();
            String email = view.getEmail();
            String telefone = view.getTelefone();

            if (!validarCampos(nome, email, telefone)) {
                return;
            }

            Suporte novo = new Suporte(nome, email, telefone);
            suportes.add(novo);
            System.out.println("Suporte criado: " + novo);

            persistirSuportes();
            view.atualizarTabela(suportes);
            view.limparSelecao();
            mostrarSucesso("Suporte salvo com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar suporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editarSuporte() {
        try {
            Suporte selecionado = view.getSuporteSelecionado();
            if (selecionado == null) {
                mostrarErro("Selecione um suporte para editar.");
                return;
            }

            String nome = view.getNome();
            String email = view.getEmail();
            String telefone = view.getTelefone();

            if (!validarCampos(nome, email, telefone)) {
                return;
            }

            selecionado.setNome(nome);
            selecionado.setEmail(email);
            selecionado.setTelefone(telefone);
            System.out.println("Suporte atualizado: " + selecionado);

            persistirSuportes();
            view.atualizarTabela(suportes);
            view.limparSelecao();
            mostrarSucesso("Suporte editado com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao editar suporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos(String nome, String email, String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            mostrarErro("Nome do suporte e obrigatorio.");
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            mostrarErro("E-mail do suporte e obrigatorio.");
            return false;
        }

        if (telefone == null || telefone.trim().isEmpty()) {
            mostrarErro("Telefone do suporte e obrigatorio.");
            return false;
        }

        return true;
    }

    private void excluirSuporte() {
        Suporte selecionado = view.getSuporteSelecionado();
        if (selecionado == null) {
            System.out.println("Selecione um suporte para excluir.");
            return;
        }

        suportes.remove(selecionado);
        persistirSuportes();
        view.atualizarTabela(suportes);
        view.limparSelecao();
        System.out.println("Suporte excluido: " + selecionado);
    }

    private void persistirSuportes() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            if (suportes == null) {
                System.err.println("Erro: Lista de suportes e nula.");
                return;
            }

            oos.writeObject(suportes);
            System.out.println("Suportes salvos com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de suportes nao encontrado - " + STORAGE_FILE);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao salvar suportes: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar suportes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Suporte> carregarSuportes() {
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
            List<Suporte> lista = (List<Suporte>) objeto;

            if (lista.isEmpty()) {
                System.out.println("Nenhum suporte carregado.");
                return lista;
            }

            ajustarNextId(lista);
            System.out.println("Suportes carregados: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de suportes nao existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de suportes esta corrompido (vazio). Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de suporte nao encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de suportes: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar suportes: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Suporte> lista) {
        int maiorId = 0;
        for (Suporte s : lista) {
            if (s.getId() > maiorId) {
                maiorId = s.getId();
            }
        }
        Suporte.setNextId(maiorId + 1);
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
