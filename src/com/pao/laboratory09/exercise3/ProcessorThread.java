package com.pao.laboratory09.exercise3;

public class ProcessorThread implements Runnable {
    private final CoadaTranzactii coada;
    public volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            while (activ || !coada.isGoala()) {
                Tranzactie t = coada.extrage();
                if (t != null) {
                    System.out.println("[Processor] Factura #" + t.id + " - " + t.suma + " RON | " + t.data);
                    Thread.sleep(80);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}