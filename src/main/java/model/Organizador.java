package model;

import java.io.Serializable;

public class Organizador implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String empresa;

    public Organizador(String nome, String email, String telefone, String empresa) {
        this.id = nextId++;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.empresa = empresa;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public static void setNextId(int nextId) {
        if (nextId > Organizador.nextId) {
            Organizador.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
