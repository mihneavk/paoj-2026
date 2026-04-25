package com.pao.proiect.eticketing.model;

public class Organizator extends Utilizator {
    private String companie;

    public Organizator(String email, String nume, String companie) {
        super(email, nume);
        this.companie = companie;
    }

    public String getCompanie() { return companie; }
    public void setCompanie(String companie) { this.companie = companie; }

    @Override
    public String getTipUtilizator() { return "ORGANIZATOR"; }
}