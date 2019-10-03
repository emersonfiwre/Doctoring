package com.intacta.doctoring.beans;

public class Serviceb {
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    String nome;
    int back;

    public Serviceb(String nome, int back) {
        this.nome = nome;
        this.back = back;
    }
}
