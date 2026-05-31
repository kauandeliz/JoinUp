package controller;

import model.Usuario;
import view.UsuarioView;

public class UsuarioController {

    private UsuarioView view;

    public UsuarioController(UsuarioView view) {
        this.view = view;

        // Vincula a ação de clique do botão ao método de salvamento
        this.view.getBotaoSalvar().setOnAction(event -> salvarUsuario());
    }

    private void salvarUsuario() {
        // 1. Pega os dados diretamente através dos métodos públicos da View
        String nome = view.getNomeDigitado();
        String email = view.getEmailDigitado();

        // 2. Instancia o objeto de modelo
        Usuario usuario = new Usuario(nome, email);

        // 3. Processa a informação
        System.out.println("Usuário salvo (Puro Java): " + usuario.getNome() + " - " + usuario.getEmail());
    }
}
