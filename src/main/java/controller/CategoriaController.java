package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Categoria;
import view.CategoriaView;

public class CategoriaController {

    private CategoriaView view;

    public CategoriaController(CategoriaView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;

        this.view.getBotaoSalvar().setOnAction(event -> salvarCategoria());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

    }

    private void salvarCategoria() {
        // 1. Pega os dados diretamente através dos métodos públicos da View
        String nome = view.getNomeDigitado();
        String descricao = view.getDescricaoDigitada();
        String classificacao = view.getClassificacaoDigitada();

        // 2. Instancia o objeto de modelo
        Categoria categoria = new Categoria(nome, descricao, classificacao);

        // 3. Processa a informação
        System.out.println("Categoria salva: " + categoria.getNome() + " - " + categoria.getDescricao() + " - "
                + categoria.getClassificacao());
    }

}
