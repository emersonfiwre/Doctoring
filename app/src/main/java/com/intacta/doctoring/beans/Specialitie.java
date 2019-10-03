package com.intacta.doctoring.beans;

public class Specialitie {
    String nome;

    public Specialitie(String nome, String key, double preco) {
        this.nome = nome;
        this.key = key;
        this.preco = preco;
    }

    public Specialitie(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;
    double preco;



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
