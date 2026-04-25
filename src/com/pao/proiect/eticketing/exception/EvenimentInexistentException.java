package com.pao.proiect.eticketing.exception;

public class EvenimentInexistentException extends RuntimeException {
    public EvenimentInexistentException(String mesaj) {
        super(mesaj);
    }
}