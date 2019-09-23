package com.intacta.doctoring.beans;

public class Agenda {
    private String doutor;
    private String id;
    private String data;


    public Agenda(String doutor, String data) {
        this.doutor = doutor;
         this.data = data;
    }

    public Agenda() {
     }

    public String getDoutor() {
        return doutor;
    }

    public void setDoutor(String doutor) {
        this.doutor = doutor;
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
