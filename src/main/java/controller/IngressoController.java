package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Ingresso;
import view.IngressoView;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller do CRUD de Ingresso.
 *
 * Controla as ações da tela, valida campos numéricos e persiste os dados
 * em arquivo, conforme a atividade de POO.
 */
public class IngressoController {

    private static final String STORAGE_FILE = "ingressos.ser";

    private IngressoView view;
    private List<Ingresso> ingressos;

    public IngressoController(IngressoView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.ingressos = carregarIngressos();

        // Atualiza a tabela com os dados já persistidos.
        this.view.atualizarTabela(ingressos);

        // Configuração dos botões da tela.
        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarIngresso());
        this.view.getBotaoExcluir().setOnAction(event -> excluirIngresso());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        // Preenche o formulário ao selecionar um ingresso na tabela.
        this.view.getTabelaIngressos().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    /**
     * Salva um novo ingresso ou atualiza o ingresso selecionado.
     */
    private void salvarOuAtualizarIngresso() {
        try {
            String nomeEvento = view.getNomeEvento();
            String tipo = view.getTipo();
            double preco = converterPreco(view.getPreco());
            int quantidade = converterQuantidade(view.getQuantidadeDisponivel());

            if (nomeEvento == null || nomeEvento.trim().isEmpty()) {
                mostrarErro("Nome do evento é obrigatório.");
                return;
            }

            if (tipo == null || tipo.trim().isEmpty()) {
                mostrarErro("Tipo do ingresso é obrigatório.");
                return;
            }

            if (preco < 0) {
                mostrarErro("Preço não pode ser negativo.");
                return;
            }

            if (quantidade < 0) {
                mostrarErro("Quantidade não pode ser negativa.");
                return;
            }

            Ingresso selecionado = view.getIngressoSelecionado();

            if (selecionado == null) {
                Ingresso novoIngresso = new Ingresso(nomeEvento.trim(), tipo.trim(), preco, quantidade);
                ingressos.add(novoIngresso);
                System.out.println("Ingresso criado: " + novoIngresso);
            } else {
                selecionado.setNomeEvento(nomeEvento.trim());
                selecionado.setTipo(tipo.trim());
                selecionado.setPreco(preco);
                selecionado.setQuantidadeDisponivel(quantidade);
                System.out.println("Ingresso atualizado: " + selecionado);
            }

            persistirIngressos();
            view.atualizarTabela(ingressos);
            view.limparSelecao();
            mostrarSucesso("Ingresso salvo com sucesso!");

        } catch (NumberFormatException e) {
            mostrarErro("Preço e quantidade devem ser números válidos.");
        } catch (Exception e) {
            mostrarErro("Erro ao salvar ingresso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exclui o ingresso selecionado na tabela.
     */
    private void excluirIngresso() {
        try {
            Ingresso selecionado = view.getIngressoSelecionado();

            if (selecionado == null) {
                mostrarErro("Selecione um ingresso para excluir.");
                return;
            }

            ingressos.remove(selecionado);
            persistirIngressos();
            view.atualizarTabela(ingressos);
            view.limparSelecao();
            mostrarSucesso("Ingresso excluído com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao excluir ingresso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Converte o preço informado em texto para double.
     * Aceita vírgula ou ponto como separador decimal.
     */
    private double converterPreco(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new NumberFormatException("Preço vazio");
        }
        return Double.parseDouble(valor.trim().replace(",", "."));
    }

    /**
     * Converte a quantidade informada em texto para inteiro.
     */
    private int converterQuantidade(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new NumberFormatException("Quantidade vazia");
        }
        return Integer.parseInt(valor.trim());
    }

    /**
     * Salva os ingressos em arquivo .ser.
     */
    private void persistirIngressos() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(ingressos);
            System.out.println("Ingressos salvos com sucesso.");

        } catch (IOException e) {
            mostrarErro("Erro ao salvar o arquivo de ingressos: " + e.getMessage());
        }
    }

    /**
     * Carrega os ingressos persistidos no arquivo.
     */
    private List<Ingresso> carregarIngressos() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object objeto = ois.readObject();

            if (!(objeto instanceof List)) {
                System.err.println("Arquivo de ingressos não contém uma lista válida.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Ingresso> lista = (List<Ingresso>) objeto;
            ajustarNextId(lista);
            System.out.println("Ingressos carregados: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de ingressos não existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de ingressos vazio. Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Classe Ingresso não encontrada: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler ingressos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar ingressos: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    /**
     * Ajusta o próximo ID com base nos dados carregados do arquivo.
     */
    private void ajustarNextId(List<Ingresso> lista) {
        int maiorId = 0;
        for (Ingresso ingresso : lista) {
            if (ingresso.getId() > maiorId) {
                maiorId = ingresso.getId();
            }
        }
        Ingresso.setNextId(maiorId + 1);
    }

    private void mostrarErro(String mensagem) {
        System.err.println("[ERRO] " + mensagem);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Operação não pode ser concluída");
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
