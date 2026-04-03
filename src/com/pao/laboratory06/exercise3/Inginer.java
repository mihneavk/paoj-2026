package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private double sold = 1000.0;

    public Inginer(String nume, String prenume, String telefon, double salariu) {
        super(nume, prenume, telefon, salariu);
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isEmpty() || parola == null || parola.isEmpty()) {
            throw new IllegalArgumentException("User sau parola invalide");
        }
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0 || suma > sold) {
            return false;
        }
        sold -= suma;
        return true;
    }

    @Override
    public int compareTo(Inginer altul) {
        if (this.nume == null && altul.nume == null) return 0;
        if (this.nume == null) return -1;
        if (altul.nume == null) return 1;
        return this.nume.compareTo(altul.nume);
    }

    @Override
    public String toString() {
        return "Inginer: " + nume + " " + prenume + ", salariu: " + salariu;
    }
}