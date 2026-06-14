package model;

import java.io.Serializable;

public class Participante implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    public Participante(String nome, String email, String cpf, String telefone) {
        this.id = nextId++;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public static void setNextId(int nextId) {
        if (nextId > Participante.nextId) {
            Participante.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
