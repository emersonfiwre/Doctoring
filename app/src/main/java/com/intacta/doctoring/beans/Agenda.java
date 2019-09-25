package com.intacta.doctoring.beans;

public class Agenda {
    private String id;


    public Agenda(String data) {
         this.id = data;
    }

    public Agenda() {
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
