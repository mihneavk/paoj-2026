package com.pao.proiect.eticketing.model;

public class Bilet {
    private BiletId biletId;
    private Eveniment eveniment;
    private double pretPlatit;

    public Bilet(BiletId biletId, Eveniment eveniment, double pretPlatit) {
        this.biletId = biletId;
        this.eveniment = eveniment;
        this.pretPlatit = pretPlatit;
    }

    public BiletId getBiletId() { return biletId; }
    public Eveniment getEveniment() { return eveniment; }
    public double getPretPlatit() { return pretPlatit; }

    @Override
    public String toString() {
        return "Bilet{" + "id=" + biletId + ", eveniment=" + eveniment.getTitlu() + ", pret=" + pretPlatit + '}';
    }
}