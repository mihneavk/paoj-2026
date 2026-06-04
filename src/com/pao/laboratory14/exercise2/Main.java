package com.pao.laboratory14.exercise2;

import com.pao.laboratory14.exercise1.TipBilet;
import com.pao.laboratory14.exercise2.model.Eveniment;
import com.pao.laboratory14.exercise2.repository.EvenimentRepository;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            EvenimentRepository repository = new EvenimentRepository();
            repository.initSchema();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String command = scanner.next();
                switch (command) {
                    case "ADD":
                        String nume = scanner.next();
                        String data = scanner.next();
                        int capacitate = scanner.nextInt();
                        TipBilet tip = TipBilet.valueOf(scanner.next());
                        Eveniment eveniment = new Eveniment(nume, data, capacitate, tip);
                        repository.save(eveniment);
                        System.out.println("Adaugat: [" + eveniment.getId() + "] " + eveniment.getNume());
                        break;
                    case "LIST":
                        repository.findAll().forEach(System.out::println);
                        break;
                    case "DELETE":
                        int id = scanner.nextInt();
                        int deleted = repository.deleteImpl(id);
                        if (deleted > 0) {
                            System.out.println("Sters: " + id);
                        } else {
                            System.out.println("Nu exista: " + id);
                        }
                        break;
                    case "COUNT":
                        System.out.println("Total: " + repository.count());
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}