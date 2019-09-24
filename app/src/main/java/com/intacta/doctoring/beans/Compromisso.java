package com.intacta.doctoring.beans;

public class Compromisso {
    public Compromisso( String time, String compromisso, String cliente) {
        this.time = time;
        this.compromisso = compromisso;
        this.cliente = cliente;
    }


    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    private boolean done = false;
    private String id;
    private String time;
    private String compromisso;
    private String cliente;



    public Compromisso(){}



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

