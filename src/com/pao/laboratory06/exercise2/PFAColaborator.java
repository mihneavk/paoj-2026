package com.pao.laboratory06.exercise2;

import java.util.Locale;
import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieliLunare;

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public void afiseaza() {
        System.out.printf(Locale.US, "PFA: %s %s, venit net anual: %.2f lei\n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "PFA";
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;
        double impozit = 0.10 * venitNet;
        
        double salariuMinimBrut = 48600; 
        double prag6 = 6 * salariuMinimBrut;
        double prag12 = 12 * salariuMinimBrut;
        double prag24 = 24 * salariuMinimBrut;
        double prag72 = 72 * salariuMinimBrut;

        double cass = 0;
        if (venitNet < prag6) {
            cass = 0.10 * prag6;
        } else if (venitNet <= prag72) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * prag72;
        }

        double cas = 0;
        if (venitNet < prag12) {
            cas = 0;
        } else if (venitNet <= prag24) {
            cas = 0.25 * prag12;
        } else {
            cas = 0.25 * prag24;
        }

        return venitNet - impozit - cass - cas;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.PFA;
    }
}