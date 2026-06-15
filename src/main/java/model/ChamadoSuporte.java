package model;

import java.io.Serializable;

public class ChamadoSuporte implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String titulo;
    private String descricao;
    private String prioridade;

    public ChamadoSuporte(String titulo, String descricao, String prioridade) {
        this.id = nextId++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public static void setNextId(int nextId) {
        if (nextId > ChamadoSuporte.nextId) {
            ChamadoSuporte.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return titulo;
    }
}
