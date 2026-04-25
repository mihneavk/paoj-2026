package com.pao.proiect.eticketing.model;

public class Client extends Utilizator {
    private boolean abonamentPremium;

    public Client(String email, String nume, boolean abonamentPremium) {
        super(email, nume);
        this.abonamentPremium = abonamentPremium;
    }

    public boolean isAbonamentPremium() { return abonamentPremium; }
    public void setAbonamentPremium(boolean abonamentPremium) { this.abonamentPremium = abonamentPremium; }

    @Override
    public String getTipUtilizator() { return "CLIENT"; }

    @Override
    public String toString() {
        return "Client{" + "nume='" + nume + "', email='" + email + "', premium=" + abonamentPremium + '}';
    }
}