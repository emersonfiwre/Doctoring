package com.intacta.doctoring.beans;

public class Cliente {

    private String nome;
    private String telefone;
    private String email;
    private String dataNascimento;

     public Cliente(String nome, String telefone, String email, String dataNascimento){
         setNome(nome);
         setTelefone(telefone);
         setEmail(email);
         setDataNascimento(dataNascimento);
     }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
