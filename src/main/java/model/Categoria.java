package model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String nome;
    private String descricao;
    private String classificacao;

    public Categoria(String nome, String descricao, String classificacao) {
        this.id = nextId++;
        this.nome = nome;
        this.descricao = descricao;
        this.classificacao = classificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public int getId() {
        return id;
    }

    public static void setNextId(int nextId) {
        if (nextId > Categoria.nextId) {
            Categoria.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return "Categoria{id=" + id + ", nome='" + nome + '\'' + ", descricao='" + descricao + '\''
                + ", classificacao='" + classificacao + '\'' + '}';
    }
}
