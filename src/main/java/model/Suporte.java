package model;

import java.io.Serializable;

public class Suporte implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String nome;
    private String email;
    private String telefone;

    public Suporte(String nome, String email, String telefone) {
        this.id = nextId++;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public static void setNextId(int nextId) {
        if (nextId > Suporte.nextId) {
            Suporte.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
