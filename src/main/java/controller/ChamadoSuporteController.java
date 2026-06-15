package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.ChamadoSuporte;
import view.ChamadoSuporteView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChamadoSuporteController {

    private static final String STORAGE_FILE = "chamados_suporte.ser";

    private ChamadoSuporteView view;
    private List<ChamadoSuporte> chamados;

    public ChamadoSuporteController(ChamadoSuporteView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.chamados = carregarChamados();

        this.view.atualizarTabela(chamados);

        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarChamado());
        this.view.getBotaoEditar().setOnAction(event -> editarChamado());
        this.view.getBotaoExcluir().setOnAction(event -> excluirChamado());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        this.view.getTabelaChamados().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarOuAtualizarChamado() {
        try {
            String titulo = view.getTitulo();
            String descricao = view.getDescricao();
            String prioridade = view.getPrioridade();

            if (titulo == null || titulo.trim().isEmpty()) {
                mostrarErro("Título do chamado é obrigatório.");
                return;
            }

            if (prioridade == null || prioridade.trim().isEmpty()) {
                mostrarErro("Prioridade do chamado é obrigatória.");
                return;
            }

            if (!prioridade.equals("Baixa") && !prioridade.equals("Média") && !prioridade.equals("Alta")) {
                mostrarErro("Prioridade deve ser Baixa, Média ou Alta.");
                return;
            }

            ChamadoSuporte selecionado = view.getChamadoSelecionado();

            if (selecionado == null) {
                ChamadoSuporte novo = new ChamadoSuporte(titulo, descricao, prioridade);
                chamados.add(novo);
                System.out.println("Chamado criado: " + novo);
            } else {
                selecionado.setTitulo(titulo);
                selecionado.setDescricao(descricao);
                selecionado.setPrioridade(prioridade);
                System.out.println("Chamado atualizado: " + selecionado);
            }

            persistirChamados();
            view.atualizarTabela(chamados);
            view.limparSelecao();
            mostrarSucesso("Chamado salvo com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar chamado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editarChamado() {
        try {
            ChamadoSuporte selecionado = view.getChamadoSelecionado();
            if (selecionado == null) {
                mostrarErro("Selecione um chamado para editar.");
                return;
            }

            String titulo = view.getTitulo();
            String descricao = view.getDescricao();
            String prioridade = view.getPrioridade();

            if (prioridade == null || prioridade.trim().isEmpty()) {
                mostrarErro("Prioridade do chamado é obrigatória.");
                return;
            }

            if (!prioridade.equals("Baixa") && !prioridade.equals("Média") && !prioridade.equals("Alta")) {
                mostrarErro("Prioridade deve ser Baixa, Média ou Alta.");
                return;
            }

            selecionado.setTitulo(titulo);
            selecionado.setDescricao(descricao);
            selecionado.setPrioridade(prioridade);
            System.out.println("Chamado atualizado: " + selecionado);

            persistirChamados();
            view.atualizarTabela(chamados);
            view.limparSelecao();
            mostrarSucesso("Chamado editado com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao editar chamado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirChamado() {
        ChamadoSuporte selecionado = view.getChamadoSelecionado();
        if (selecionado == null) {
            System.out.println("Selecione um chamado para excluir.");
            return;
        }

        chamados.remove(selecionado);
        persistirChamados();
        view.atualizarTabela(chamados);
        view.limparSelecao();
        System.out.println("Chamado excluido: " + selecionado);
    }

    private void persistirChamados() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            if (chamados == null) {
                System.err.println("Erro: Lista de chamados e nula.");
                return;
            }

            oos.writeObject(chamados);
            System.out.println("Chamados salvos com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de chamados nao encontrado - " + STORAGE_FILE);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao salvar chamados: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar chamados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<ChamadoSuporte> carregarChamados() {
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
            List<ChamadoSuporte> lista = (List<ChamadoSuporte>) objeto;

            if (lista.isEmpty()) {
                System.out.println("Nenhum chamado carregado.");
                return lista;
            }

            ajustarNextId(lista);
            System.out.println("Chamados carregados: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de chamados nao existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de chamados esta corrompido (vazio). Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de chamado nao encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de chamados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar chamados: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<ChamadoSuporte> lista) {
        int maiorId = 0;
        for (ChamadoSuporte c : lista) {
            if (c.getId() > maiorId) {
                maiorId = c.getId();
            }
        }
        ChamadoSuporte.setNextId(maiorId + 1);
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
