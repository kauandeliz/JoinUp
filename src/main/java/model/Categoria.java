package model;

public class Categoria {
    private String nome;
    private String descricao;
    private String classificacao;

    public Categoria(String nome, String descricao, String classificacao) {
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
}
