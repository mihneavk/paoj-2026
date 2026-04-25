package com.pao.proiect.eticketing;

import com.pao.proiect.eticketing.exception.BileteEpuizateException;
import com.pao.proiect.eticketing.exception.EvenimentInexistentException;
import com.pao.proiect.eticketing.exception.UtilizatorInexistentException;
import com.pao.proiect.eticketing.model.Client;
import com.pao.proiect.eticketing.model.Eveniment;
import com.pao.proiect.eticketing.model.Locatie;
import com.pao.proiect.eticketing.model.Organizator;
import com.pao.proiect.eticketing.service.CatalogService;
import com.pao.proiect.eticketing.service.TicketingService;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CatalogService catalog = CatalogService.getInstanta();
        TicketingService ticketing = TicketingService.getInstanta();
        List<Locatie> locatii = new ArrayList<>();

        Scanner scannerTastatura = new Scanner(System.in);
        Scanner scanner = scannerTastatura;
        boolean modulFisier = false;

        System.out.print("Vrei sa citesti datele din input.txt? (da/nu): ");
        String raspuns = scannerTastatura.nextLine().trim().toLowerCase();

        if (raspuns.equals("da")) {
            try {
                scanner = new Scanner(new File("src/com/pao/proiect/eticketing/input.txt"));
                modulFisier = true;
                System.out.println("S-a activat citirea automata din fisier.\n");
            } catch (FileNotFoundException e) {
                System.out.println("Fisierul input.txt nu a fost gasit. Se va folosi tastatura ca metoda default.\n");
            }
        } else {
            System.out.println("S-a activat citirea de la tastatura.\n");
        }

        boolean ruleaza = true;

        while (ruleaza) {
            if (modulFisier && !scanner.hasNextLine()) {
                break;
            }

            if (!modulFisier) {
                System.out.println("\n--- MENIU E-TICKETING ---");
                System.out.println("1. Adauga o locatie noua");
                System.out.println("2. Inregistreaza un utilizator nou (Client/Organizator)");
                System.out.println("3. Adauga un eveniment nou");
                System.out.println("4. Listeaza toate evenimentele");
                System.out.println("5. Cauta un eveniment");
                System.out.println("6. Verifica locuri disponibile");
                System.out.println("7. Cumpara bilet");
                System.out.println("8. Afiseaza istoric bilete client");
                System.out.println("9. Sterge un eveniment");
                System.out.println("10. Afiseaza toti utilizatorii");
                System.out.println("0. Iesire");
                System.out.print("Alege o optiune: ");
            }

            if (!scanner.hasNextLine()) {
                break;
            }

            String optiune = scanner.nextLine();

            if (modulFisier) {
                System.out.println("\n>>> Se executa optiunea: " + optiune);
            }

            try {
                switch (optiune) {
                    case "1":
                        if (!modulFisier) System.out.print("Adresa locatie: ");
                        String adresa = scanner.nextLine();
                        if (!modulFisier) System.out.print("Capacitate maxima: ");
                        int capacitate = Integer.parseInt(scanner.nextLine());
                        locatii.add(new Locatie(adresa, capacitate));
                        System.out.println("Locatie salvata: " + adresa + " (Capacitate: " + capacitate + ")");
                        break;

                    case "2":
                        if (!modulFisier) System.out.print("Tip utilizator (1 - Client, 2 - Organizator): ");
                        String tip = scanner.nextLine();
                        if (!modulFisier) System.out.print("Nume: ");
                        String nume = scanner.nextLine();
                        if (!modulFisier) System.out.print("Email: ");
                        String email = scanner.nextLine();

                        if (tip.equals("1")) {
                            if (!modulFisier) System.out.print("Abonament premium (true/false): ");
                            boolean premium = Boolean.parseBoolean(scanner.nextLine());
                            Client client = new Client(email, nume, premium);
                            catalog.adaugaUtilizator(client);
                            System.out.println("Client inregistrat: " + nume);
                        } else if (tip.equals("2")) {
                            if (!modulFisier) System.out.print("Companie organizatoare: ");
                            String companie = scanner.nextLine();
                            Organizator organizator = new Organizator(email, nume, companie);
                            catalog.adaugaUtilizator(organizator);
                            System.out.println("Organizator inregistrat: " + nume);
                        } else {
                            System.out.println("Tip utilizator invalid.");
                        }
                        break;

                    case "3":
                        if (locatii.isEmpty()) {
                            System.out.println("Eroare: Nu exista locatii definite.");
                            break;
                        }
                        
                        if (!modulFisier) System.out.print("Email organizator: ");
                        String emailOrg = scanner.nextLine();
                        Organizator organizatorGasit = catalog.cautaOrganizator(emailOrg);
                        
                        if (!modulFisier) System.out.print("ID Eveniment: ");
                        int idEv = Integer.parseInt(scanner.nextLine());
                        if (!modulFisier) System.out.print("Titlu Eveniment: ");
                        String titlu = scanner.nextLine();
                        if (!modulFisier) System.out.print("Pret bilet: ");
                        double pret = Double.parseDouble(scanner.nextLine());
                        
                        Locatie locatieAleasa = locatii.get(0);
                        Eveniment eveniment = new Eveniment(idEv, titlu, locatieAleasa, LocalDateTime.now().plusDays(30), pret, organizatorGasit);
                        catalog.adaugaEveniment(eveniment);
                        System.out.println("Eveniment creat: " + titlu);
                        break;

                    case "4":
                        System.out.println("--- Evenimente Disponibile ---");
                        catalog.listeazaEvenimente();
                        break;

                    case "5":
                        if (!modulFisier) System.out.print("ID eveniment cautat: ");
                        int idCautat = Integer.parseInt(scanner.nextLine());
                        Eveniment evGasit = catalog.cautaEveniment(idCautat);
                        System.out.println("Rezultat cautare: " + evGasit);
                        break;

                    case "6":
                        if (!modulFisier) System.out.print("ID eveniment verificare: ");
                        int idVerificare = Integer.parseInt(scanner.nextLine());
                        Eveniment evVerificare = catalog.cautaEveniment(idVerificare);
                        System.out.println("Locuri ramase la " + evVerificare.getTitlu() + ": " + evVerificare.getLocuriDisponibile());
                        break;

                    case "7":
                        if (!modulFisier) System.out.print("Email client: ");
                        String emailClient = scanner.nextLine();
                        if (!modulFisier) System.out.print("ID Eveniment: ");
                        int idCumparare = Integer.parseInt(scanner.nextLine());
                        
                        Client clientCumparator = new Client(emailClient, "Nedefinit", false);
                        Eveniment evCumparare = catalog.cautaEveniment(idCumparare);
                        
                        ticketing.cumparaBilet(clientCumparator, evCumparare);
                        System.out.println("Tranzactie reusita pentru " + emailClient);
                        break;

                    case "8":
                        if (!modulFisier) System.out.print("Email client istoric: ");
                        String emailIstoric = scanner.nextLine();
                        System.out.println("--- Istoric Bilete (" + emailIstoric + ") ---");
                        ticketing.afiseazaBileteClient(emailIstoric);
                        break;

                    case "9":
                        if (!modulFisier) System.out.print("ID eveniment de sters: ");
                        int idStergere = Integer.parseInt(scanner.nextLine());
                        catalog.stergeEveniment(idStergere);
                        System.out.println("Eveniment sters din sistem.");
                        break;

                    case "10":
                        System.out.println("--- Lista Utilizatori ---");
                        catalog.afiseazaUtilizatori();
                        break;

                    case "0":
                        ruleaza = false;
                        System.out.println("Sesiune inchisa cu succes.");
                        break;

                    default:
                        System.out.println("Optiune inexistenta.");
                }
            } catch (EvenimentInexistentException | BileteEpuizateException | UtilizatorInexistentException e) {
                System.out.println("Actiune respinsa: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Eroare tip date: S-a asteptat o valoare numerica.");
            }
        }

        if (modulFisier) {
            System.out.println("\nProcesarea fisierului a fost finalizata.");
            scanner.close();
        }
        scannerTastatura.close();
    }
}