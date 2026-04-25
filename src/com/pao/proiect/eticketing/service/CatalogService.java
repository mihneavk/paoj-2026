package com.pao.proiect.eticketing.service;

import com.pao.proiect.eticketing.exception.EvenimentInexistentException;
import com.pao.proiect.eticketing.exception.UtilizatorInexistentException;
import com.pao.proiect.eticketing.model.Eveniment;
import com.pao.proiect.eticketing.model.Organizator;
import com.pao.proiect.eticketing.model.Utilizator;

import java.util.*;

public class CatalogService {
    private static CatalogService instanta;
    
    private Set<Eveniment> evenimenteSortate = new TreeSet<>();
    private Map<Integer, Eveniment> indexEvenimente = new HashMap<>();
    private List<Utilizator> utilizatori = new ArrayList<>();

    private CatalogService() {}

    public static CatalogService getInstanta() {
        if (instanta == null) {
            instanta = new CatalogService();
        }
        return instanta;
    }

    public void adaugaUtilizator(Utilizator u) {
        if (u != null) {
            utilizatori.add(u);
        }
    }

    public Organizator cautaOrganizator(String email) {
        for (Utilizator u : utilizatori) {
            if (u instanceof Organizator && u.getEmail().equals(email)) {
                return (Organizator) u;
            }
        }
        throw new UtilizatorInexistentException("Organizatorul cu email-ul " + email + " nu exista in sistem.");
    }

    public void afiseazaUtilizatori() {
        for (Utilizator u : utilizatori) {
            System.out.println(u.getNume() + " - " + u.getTipUtilizator());
        }
    }

    public void adaugaEveniment(Eveniment e) {
        if (e != null) {
            evenimenteSortate.add(e);
            indexEvenimente.put(e.getId(), e);
        }
    }

    public Eveniment cautaEveniment(int id) {
        if (!indexEvenimente.containsKey(id)) {
            throw new EvenimentInexistentException("Evenimentul cu ID-ul " + id + " nu a fost gasit.");
        }
        return indexEvenimente.get(id);
    }

    public void listeazaEvenimente() {
        for (Eveniment e : evenimenteSortate) {
            System.out.println(e);
        }
    }

    public void stergeEveniment(int id) {
        Eveniment e = cautaEveniment(id);
        evenimenteSortate.remove(e);
        indexEvenimente.remove(id);
    }
}