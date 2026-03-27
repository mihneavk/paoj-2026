package com.pao.laboratory05.biblioteca;

public class Main {
    public static void main(String[] args) {
        BibliotecaService biblioteca = BibliotecaService.getInstance();
        
        biblioteca.addCarte(new Carte("Ion", "Liviu Rebreanu", 1920, 4.5));
        biblioteca.addCarte(new Carte("Morometii", "Marin Preda", 1955, 4.8));
        biblioteca.addCarte(new Carte("Enigma Otiliei", "George Calinescu", 1938, 4.3));
        biblioteca.addCarte(new Carte("Baltagul", "Mihail Sadoveanu", 1930, 4.6));

        System.out.println("\n--- Dupa rating (descrescator) ---");
        biblioteca.listSortedByRating();

        System.out.println("\n--- Dupa an (crescator) ---");
        biblioteca.listSortedBy(new CarteAnComparator());

        System.out.println("\n--- Dupa autor (alfabetic) ---");
        biblioteca.listSortedBy(new CarteAutorComparator());
    }
}