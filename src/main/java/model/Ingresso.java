package model;

import java.io.Serializable;

/**
 * Classe de domínio que representa um tipo de ingresso vendido no JoinUp.
 *
 * A classe possui mais de 3 atributos e permite controlar preço,
 * quantidade disponível e tipo do ingresso.
 */
public class Ingresso implements Serializable {
    private static final long serialVersionUID = 1L;

    // Controla o próximo ID automático dos ingressos cadastrados.
    private static int nextId = 1;

    private int id;
    private String nomeEvento;
    private String tipo;
    private double preco;
    private int quantidadeDisponivel;

    /**
     * Construtor usado no cadastro de um novo ingresso.
     */
    public Ingresso(String nomeEvento, String tipo, double preco, int quantidadeDisponivel) {
        this.id = nextId++;
        this.nomeEvento = nomeEvento;
        this.tipo = tipo;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    /**
     * Ajusta o próximo ID ao carregar dados persistidos.
     */
    public static void setNextId(int nextId) {
        if (nextId > Ingresso.nextId) {
            Ingresso.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return "Ingresso{id=" + id + ", nomeEvento='" + nomeEvento + '\''
                + ", tipo='" + tipo + '\''
                + ", preco=" + preco
                + ", quantidadeDisponivel=" + quantidadeDisponivel + '}';
    }
}
