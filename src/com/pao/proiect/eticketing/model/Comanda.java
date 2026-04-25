package com.pao.proiect.eticketing.model;

import java.util.List;

public class Comanda {
    private String idComanda;
    private Client client;
    private List<Bilet> bilete;

    public Comanda(String idComanda, Client client, List<Bilet> bilete) {
        this.idComanda = idComanda;
        this.client = client;
        this.bilete = bilete;
    }

    public String getIdComanda() { return idComanda; }
    public Client getClient() { return client; }
    public List<Bilet> getBilete() { return bilete; }
}