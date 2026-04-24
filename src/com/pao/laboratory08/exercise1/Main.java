package com.pao.laboratory08.exercise1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String nume = parts[0].trim();
                    int varsta = Integer.parseInt(parts[1].trim());
                    String oras = parts[2].trim();
                    String strada = parts[3].trim();
                    studenti.add(new Student(nume, varsta, new Adresa(oras, strada)));
                }
            }
        }

        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String commandLine = scanner.nextLine().trim();
            String[] commandParts = commandLine.split(" ", 2);
            String command = commandParts[0];

            if (command.equals("PRINT")) {
                for (Student s : studenti) {
                    System.out.println(s);
                }
            } else if (command.equals("SHALLOW") && commandParts.length == 2) {
                String nume = commandParts[1];
                Student target = null;
                for (Student s : studenti) {
                    if (s.getNume().equals(nume)) {
                        target = s;
                        break;
                    }
                }
                if (target != null) {
                    Student clona = target.shallowClone();
                    clona.getAdresa().setOras("MODIFICAT");
                    System.out.println("Original: " + target);
                    System.out.println("Clona: " + clona);
                }
            } else if (command.equals("DEEP") && commandParts.length == 2) {
                String nume = commandParts[1];
                Student target = null;
                for (Student s : studenti) {
                    if (s.getNume().equals(nume)) {
                        target = s;
                        break;
                    }
                }
                if (target != null) {
                    Student clona = target.deepClone();
                    clona.getAdresa().setOras("MODIFICAT");
                    System.out.println("Original: " + target);
                    System.out.println("Clona: " + clona);
                }
            }
        }
    }
}