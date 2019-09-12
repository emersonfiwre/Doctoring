package com.intacta.doctoring.beans;

public class Compromisso {
    private String data;
    private String cliente;
    private String id;



    private String compromissos;

    public Compromisso(String data, String cliente, String id, String compromissos) {
        this.data = data;
        this.cliente = cliente;
        this.id = id;
        this.compromissos = compromissos;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCompromissos() {
        return compromissos;
    }

    public void setCompromissos(String compromissos) {
        this.compromissos = compromissos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

