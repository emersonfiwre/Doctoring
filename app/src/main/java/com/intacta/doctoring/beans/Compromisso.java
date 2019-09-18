package com.intacta.doctoring.beans;

public class Compromisso {
    private String id;
    private String data;
    private String time;
    private String compromisso;
    private String cliente;

    public Compromisso( String data, String time, String compromisso, String cliente ) {
        this.data = data;
        this.time = time;
        this.compromisso = compromisso;
        this.cliente = cliente;

    }

    public Compromisso(){}


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

    public String getCompromisso() {
        return compromisso;
    }

    public void setCompromisso(String compromisso) {
        this.compromisso = compromisso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

