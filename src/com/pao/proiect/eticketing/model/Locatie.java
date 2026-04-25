package com.pao.proiect.eticketing.model;

public class Locatie {
    private String adresa;
    private int capacitateMaxima;

    public Locatie(String adresa, int capacitateMaxima) {
        this.adresa = adresa;
        this.capacitateMaxima = capacitateMaxima;
    }

    public String getAdresa() { return adresa; }
    public void setAdresa(String adresa) { this.adresa = adresa; }
    public int getCapacitateMaxima() { return capacitateMaxima; }
    public void setCapacitateMaxima(int capacitateMaxima) { this.capacitateMaxima = capacitateMaxima; }

    @Override
    public String toString() {
        return adresa + " (Capacitate: " + capacitateMaxima + ")";
    }
}