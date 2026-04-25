package com.pao.proiect.eticketing.service;

import com.pao.proiect.eticketing.exception.BileteEpuizateException;
import com.pao.proiect.eticketing.model.Bilet;
import com.pao.proiect.eticketing.model.BiletId;
import com.pao.proiect.eticketing.model.Client;
import com.pao.proiect.eticketing.model.Eveniment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TicketingService {
    private static TicketingService instanta;
    
    private Map<String, List<Bilet>> istoricClienti = new HashMap<>();

    private TicketingService() {}

    public static TicketingService getInstanta() {
        if (instanta == null) {
            instanta = new TicketingService();
        }
        return instanta;
    }

    public Bilet cumparaBilet(Client client, Eveniment eveniment) throws BileteEpuizateException {
        if (client == null || eveniment == null) {
            throw new IllegalArgumentException("Clientul si evenimentul nu pot fi nule.");
        }
        
        if (eveniment.getLocuriDisponibile() <= 0) {
            throw new BileteEpuizateException("Nu mai sunt locuri la " + eveniment.getTitlu());
        }

        eveniment.rezervaLoc();
        
        BiletId idGenerat = new BiletId(UUID.randomUUID().toString(), LocalDateTime.now());
        Bilet biletNou = new Bilet(idGenerat, eveniment, eveniment.getPret());

        istoricClienti.putIfAbsent(client.getEmail(), new ArrayList<>());
        istoricClienti.get(client.getEmail()).add(biletNou);

        return biletNou;
    }

    public void afiseazaBileteClient(String emailClient) {
        List<Bilet> bilete = istoricClienti.getOrDefault(emailClient, new ArrayList<>());
        if (bilete.isEmpty()) {
            System.out.println("Clientul nu are bilete achizitionate.");
            return;
        }
        for (Bilet b : bilete) {
            System.out.println(b);
        }
    }
}