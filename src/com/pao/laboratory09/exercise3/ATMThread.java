package com.pao.laboratory09.exercise3;

public class ATMThread extends Thread {
    private final CoadaTranzactii coada;
    private final int id;

    public ATMThread(int id, CoadaTranzactii coada) {
        super("ATM-" + id);
        this.id = id;
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {
                int tranzactieId = id * 100 + i;
                double suma = 100.0 * i;
                Tranzactie t = new Tranzactie(tranzactieId, suma, "2024-05-14");
                
                System.out.println("[" + getName() + "] trimite: Tranzactie #" + tranzactieId + " " + suma + " RON");
                coada.adauga(t);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}