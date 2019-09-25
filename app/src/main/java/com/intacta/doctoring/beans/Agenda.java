package com.intacta.doctoring.beans;

public class Agenda {
    private String id;
    private String data;


    public Agenda(String doutor, String data) {
         this.data = data;
    }

    public Agenda() {
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
