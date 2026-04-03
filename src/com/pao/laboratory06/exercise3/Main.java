package com.pao.laboratory06.exercise3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Inginer i1 = new Inginer("Zaharia", "Ion", "0700000001", 5000);
        Inginer i2 = new Inginer("Avram", "Mihai", null, 8000);
        Inginer i3 = new Inginer("Ionescu", "Ana", "0700000002", 6500);

        Inginer[] ingineri = {i1, i2, i3};

        System.out.println("Sortare naturala (dupa nume):");
        Arrays.sort(ingineri);
        for (Inginer i : ingineri) {
            System.out.println(i);
        }

        System.out.println("\nSortare custom (dupa salariu descrescator):");
        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        for (Inginer i : ingineri) {
            System.out.println(i);
        }

        System.out.println("\nAcces prin referinta PlataOnline:");
        PlataOnline refPlata = i1;
        refPlata.autentificare("user", "pass");
        System.out.println("Sold initial: " + refPlata.consultareSold());
        refPlata.efectuarePlata(200);
        System.out.println("Sold dupa plata: " + refPlata.consultareSold());

        System.out.println("\nTestare constante:");
        System.out.println("Valoare TVA: " + ConstanteFinanciare.TVA.getValoare());
        System.out.println("Salariu minim: " + ConstanteFinanciare.SALARIU_MINIM.getValoare());

        System.out.println("\nAcces prin referinta PlataOnlineSMS:");
        PersoanaJuridica pj1 = new PersoanaJuridica("Tech", "SRL", "0711111111");
        PersoanaJuridica pjFaraTelefon = new PersoanaJuridica("FaraTel", "SRL", null);
        
        PlataOnlineSMS refSMS1 = pj1;
        boolean status1 = refSMS1.trimiteSMS("Plata aprobata");
        System.out.println("Trimitere SMS cu telefon valid: " + status1);
        System.out.println("Mesaje inregistrate: " + pj1.getSmsTrimise().size());

        boolean status2 = pjFaraTelefon.trimiteSMS("Plata aprobata");
        System.out.println("Trimitere SMS fara telefon valid: " + status2);

        System.out.println("\nTratarea exceptiilor:");
        try {
            refPlata.trimiteSMS("Test");
        } catch (UnsupportedOperationException e) {
            System.out.println("Prins UnsupportedOperationException: " + e.getMessage());
        }

        try {
            refPlata.autentificare("", null);
        } catch (IllegalArgumentException e) {
            System.out.println("Prins IllegalArgumentException la autentificare: " + e.getMessage());
        }
    }
}