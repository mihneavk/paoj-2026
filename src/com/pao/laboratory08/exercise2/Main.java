package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;
import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int prag = 0;
        
        if (scanner.hasNextInt()) {
            prag = scanner.nextInt();
        }
        scanner.close();

        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String nume = parts[0].trim();
                    int varsta = Integer.parseInt(parts[1].trim());
                    String oras = parts[2].trim();
                    String strada = parts[3].trim();

                    if (varsta >= prag) {
                        Adresa adresa = new Adresa(oras, strada);
                        Student student = new Student(nume, varsta, adresa);
                        studenti.add(student);
                    }
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rezultate.txt"))) {
            for (Student s : studenti) {
                bw.write(s.toString());
                bw.newLine();
            }
        }

        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + studenti.size() + " studenti\n");

        for (Student s : studenti) {
            System.out.println(s);
        }

        System.out.println("\nScris in: rezultate.txt");
    }
}