package com.pao.proiect.eticketing.model;

public abstract class Utilizator {
    protected String email;
    protected String nume;

    public Utilizator(String email, String nume) {
        this.email = email;
        this.nume = nume;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public abstract String getTipUtilizator();
}