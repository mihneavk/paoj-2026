package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        List<Tranzactie> tranzactii = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String contSursa = scanner.next();
            String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie tranzactie = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            tranzactie.note = "procesat";
            tranzactii.add(tranzactie);
        }

        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(tranzactii);
        }

        List<Tranzactie> tranzactiiDeserializate;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            tranzactiiDeserializate = (List<Tranzactie>) ois.readObject();
        }

        while (scanner.hasNext()) {
            String command = scanner.next();

            if (command.equals("LIST")) {
                for (Tranzactie t : tranzactiiDeserializate) {
                    System.out.println(t);
                }
            } else if (command.equals("FILTER")) {
                String prefix = scanner.next();
                boolean found = false;
                for (Tranzactie t : tranzactiiDeserializate) {
                    if (t.data.startsWith(prefix)) {
                        System.out.println(t);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Niciun rezultat.");
                }
            } else if (command.equals("NOTE")) {
                int id = scanner.nextInt();
                boolean found = false;
                for (Tranzactie t : tranzactiiDeserializate) {
                    if (t.id == id) {
                        System.out.println("NOTE[" + id + "]: " + t.note);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("NOTE[" + id + "]: not found");
                }
            }
        }

        scanner.close();
    }
}