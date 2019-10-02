package com.intacta.doctoring.beans;

public class Specialitie {
    String nome;
    double preco;

    public Specialitie(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Specialitie() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
