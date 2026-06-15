package model;

import java.io.Serializable;

/**
 * Classe de domínio que representa um artista do JoinUp.
 *
 * Esta classe faz parte da minha entrega individual de POO.
 * Ela é Serializable porque os dados serão gravados em arquivo .ser,
 * conforme a exigência da atividade.
 */
public class Artista implements Serializable {
    private static final long serialVersionUID = 1L;

    // Controla o próximo ID automático dos artistas cadastrados.
    private static int nextId = 1;

    private int id;
    private String nome;
    private String generoMusical;
    private String cidadeOrigem;

    /**
     * Construtor usado no cadastro de um novo artista.
     * O ID é gerado automaticamente para facilitar o CRUD.
     */
    public Artista(String nome, String generoMusical, String cidadeOrigem) {
        this.id = nextId++;
        this.nome = nome;
        this.generoMusical = generoMusical;
        this.cidadeOrigem = cidadeOrigem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public String getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(String cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    /**
     * Ajusta o próximo ID ao carregar dados salvos em arquivo.
     * Isso evita repetir IDs depois que o programa é fechado e aberto novamente.
     */
    public static void setNextId(int nextId) {
        if (nextId > Artista.nextId) {
            Artista.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return "Artista{id=" + id + ", nome='" + nome + '\''
                + ", generoMusical='" + generoMusical + '\''
                + ", cidadeOrigem='" + cidadeOrigem + '\'' + '}';
    }
}
