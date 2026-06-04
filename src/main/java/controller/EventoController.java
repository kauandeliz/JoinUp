package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Evento;
import view.EventoView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoController {

    private static final String STORAGE_FILE = "Eventos.ser";

    private EventoView view;
    private List<Evento> eventos;

    public EventoController(EventoView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.eventos = carregarEventos();

        // atualiza a tabela com os dados persistidos
        this.view.atualizarTabela(eventos);
        this.view.getBotaoSalvar().setOnAction(event -> salvarEvento());
        this.view.getBotaoAtualizar().setOnAction(event -> atualizarEvento());
        this.view.getBotaoExcluir().setOnAction(event -> excluirEvento());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        // atualiza campos do painel de edição quando o usuário seleciona uma linha
        this.view.getTabelaEventos().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCamposSelecao(selecionado));
    }

    private void salvarEvento() {
        String nome = view.getNomeDigitado();
        String data = view.getDataDigitada();
        String endereco = view.getEnderecoDigitado();

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome do evento é obrigatório.");
            return;
        }

        Evento evento = new Evento(nome, data, endereco);
        eventos.add(evento);
        persistirEventos();

        view.atualizarTabela(eventos);
        view.limparCamposCadastro();
        System.out.println("Evento salva: " + evento);
    }

    private void atualizarEvento() {
        Evento selecionado = view.getTabelaEventos().getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            System.out.println("Selecione uma Evento para atualizar.");
            return;
        }

        String nome = view.getNomeSelecionado();
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome da Evento é obrigatório para atualização.");
            return;
        }

        selecionado.setNome(nome);
        selecionado.setData(view.getDataSelecionada());
        selecionado.setEndereco(view.getEnderecoSelecionado());

        persistirEventos();
        view.atualizarTabela(eventos);
        System.out.println("Evento atualizado: " + selecionado);
    }

    private void excluirEvento() {
        Evento selecionado = view.getTabelaEventos().getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            System.out.println("Selecione uma Evento para excluir.");
            return;
        }

        eventos.remove(selecionado);
        persistirEventos();
        view.atualizarTabela(eventos);
        view.limparCamposSelecao();
        System.out.println("Evento excluída: " + selecionado);
    }

    private void persistirEventos() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(eventos);
        } catch (IOException e) {
            System.out.println("Erro ao salvar Eventos: " + e.getMessage());
        }
    }

    private List<Evento> carregarEventos() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object objeto = ois.readObject();
            if (objeto instanceof List) {
                @SuppressWarnings("unchecked")
                List<Evento> lista = (List<Evento>) objeto;
                ajustarNextId(lista);
                System.out.println("Eventos carregadas: " + lista.size());
                return lista;
            }

            System.out.println("Arquivo de Eventos não contém lista válida. Iniciando vazio.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Arquivo de persistência não encontrado ou inválido. Iniciando lista vazia.");
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Evento> lista) {
        int maiorId = 0;
        for (Evento evento : lista) {
            if (evento.getId() > maiorId) {
                maiorId = evento.getId();
            }
        }
        Evento.setNextId(maiorId + 1);
    }

}
