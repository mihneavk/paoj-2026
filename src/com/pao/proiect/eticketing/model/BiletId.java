package com.pao.proiect.eticketing.model;

import java.time.LocalDateTime;

public final class BiletId {
    private final String codUnic;
    private final LocalDateTime dataEmitere;

    public BiletId(String codUnic, LocalDateTime dataEmitere) {
        this.codUnic = codUnic;
        this.dataEmitere = dataEmitere;
    }

    public String getCodUnic() { return codUnic; }
    public LocalDateTime getDataEmitere() { return dataEmitere; }

    @Override
    public String toString() { return codUnic; }
}