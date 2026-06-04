package model;

import java.io.Serializable;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String nome;
    private String data;
    private String endereco;

    public Evento(String nome, String data, String endereco) {
        this.id = nextId++;
        this.nome = nome;
        this.data = data;
        this.endereco = endereco;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public static void setNextId(int nextId){
        if (nextId > Evento.nextId) {
            Evento.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return "Evento{id=" + id + ", nome='" + nome + '\'' + ", data='" + data + '\''
                + ", endereco='" + endereco + '\'' + '}';
    }
}


