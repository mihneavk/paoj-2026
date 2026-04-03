package com.pao.laboratory06.exercise2;

import java.util.Locale;
import java.util.Scanner;

public class SRLColaborator extends Colaborator implements PersoanaJuridica {
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
        System.out.printf(Locale.US, "SRL: %s %s, venit net anual: %.2f lei\n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "SRL";
    }

    @Override
    public double calculeazaVenitNetAnual() {
        return (venitBrutLunar - cheltuieliLunare) * 12 * 0.84;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.SRL;
    }
}