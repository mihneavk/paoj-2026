package com.pao.proiect.eticketing.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Eveniment implements Comparable<Eveniment> {
    private int id;
    private String titlu;
    private Locatie locatie;
    private LocalDateTime dataTimp;
    private int locuriOcupate;
    private double pret;
    private Organizator organizator;

    public Eveniment(int id, String titlu, Locatie locatie, LocalDateTime dataTimp, double pret, Organizator organizator) {
        this.id = id;
        this.titlu = titlu;
        this.locatie = locatie;
        this.dataTimp = dataTimp;
        this.pret = pret;
        this.organizator = organizator;
        this.locuriOcupate = 0;
    }

    public int getId() { return id; }
    public String getTitlu() { return titlu; }
    public Locatie getLocatie() { return locatie; }
    public LocalDateTime getDataTimp() { return dataTimp; }
    public int getLocuriOcupate() { return locuriOcupate; }
    public double getPret() { return pret; }
    public Organizator getOrganizator() { return organizator; }

    public void rezervaLoc() { this.locuriOcupate++; }
    
    public int getLocuriDisponibile() {
        return locatie.getCapacitateMaxima() - locuriOcupate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eveniment eveniment = (Eveniment) o;
        return id == eveniment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Eveniment altEveniment) {
        return this.dataTimp.compareTo(altEveniment.dataTimp);
    }

    @Override
    public String toString() {
        return "Eveniment{" + "id=" + id + ", titlu='" + titlu + "', organizator='" + organizator.getNume() + "', data=" + dataTimp + ", locuriDisponibile=" + getLocuriDisponibile() + '}';
    }
}