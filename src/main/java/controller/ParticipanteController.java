package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Participante;
import view.ParticipanteView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteController {

    private static final String STORAGE_FILE = "participantes.ser";

    private ParticipanteView view;
    private List<Participante> participantes;

    public ParticipanteController(ParticipanteView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.participantes = carregarParticipantes();

        this.view.atualizarTabela(participantes);

        this.view.getBotaoSalvar().setOnAction(event -> salvarParticipante());
        this.view.getBotaoEditar().setOnAction(event -> editarParticipante());
        this.view.getBotaoExcluir().setOnAction(event -> excluirParticipante());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        this.view.getTabelaParticipantes().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarParticipante() {
        try {
            String nome = view.getNome();
            String email = view.getEmail();
            String cpf = view.getCpf();
            String telefone = view.getTelefone();

            if (!validarCampos(nome, email, cpf, telefone)) {
                return;
            }

            Participante novoParticipante = new Participante(nome, email, cpf, telefone);
            participantes.add(novoParticipante);
            System.out.println("Participante criado: " + novoParticipante);

            persistirParticipantes();
            view.atualizarTabela(participantes);
            view.limparSelecao();
            mostrarSucesso("Participante salvo com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar participante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editarParticipante() {
        try {
            Participante selecionado = view.getParticipanteSelecionado();
            if (selecionado == null) {
                mostrarErro("Selecione um participante para editar.");
                return;
            }

            String nome = view.getNome();
            String email = view.getEmail();
            String cpf = view.getCpf();
            String telefone = view.getTelefone();

            if (!validarCampos(nome, email, cpf, telefone)) {
                return;
            }

            selecionado.setNome(nome);
            selecionado.setEmail(email);
            selecionado.setCpf(cpf);
            selecionado.setTelefone(telefone);
            System.out.println("Participante atualizado: " + selecionado);

            persistirParticipantes();
            view.atualizarTabela(participantes);
            view.limparSelecao();
            mostrarSucesso("Participante editado com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao editar participante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos(String nome, String email, String cpf, String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            mostrarErro("Nome do participante e obrigatorio.");
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            mostrarErro("Email do participante e obrigatorio.");
            return false;
        }

        if (cpf == null || cpf.trim().isEmpty()) {
            mostrarErro("CPF do participante e obrigatorio.");
            return false;
        }

        if (telefone == null || telefone.trim().isEmpty()) {
            mostrarErro("Telefone do participante e obrigatorio.");
            return false;
        }

        return true;
    }

    private void excluirParticipante() {
        Participante selecionado = view.getParticipanteSelecionado();
        if (selecionado == null) {
            System.out.println("Selecione um participante para excluir.");
            return;
        }

        participantes.remove(selecionado);
        persistirParticipantes();
        view.atualizarTabela(participantes);
        view.limparSelecao();
        System.out.println("Participante excluido: " + selecionado);
    }

    private void persistirParticipantes() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            if (participantes == null) {
                System.err.println("Erro: Lista de participantes e nula.");
                return;
            }

            oos.writeObject(participantes);
            System.out.println("Participantes salvos com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de participantes nao encontrado - " + STORAGE_FILE);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao salvar participantes: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar participantes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Participante> carregarParticipantes() {
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
            List<Participante> lista = (List<Participante>) objeto;

            if (lista.isEmpty()) {
                System.out.println("Nenhum participante carregado.");
                return lista;
            }

            ajustarNextId(lista);
            System.out.println("Participantes carregados: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de participantes nao existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de participantes esta corrompido (vazio). Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de participante nao encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de participantes: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar participantes: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Participante> lista) {
        int maiorId = 0;
        for (Participante participante : lista) {
            if (participante.getId() > maiorId) {
                maiorId = participante.getId();
            }
        }
        Participante.setNextId(maiorId + 1);
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
