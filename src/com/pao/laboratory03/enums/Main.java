package com.pao.laboratory03.enums;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Toate prioritatile ===");
        for (Priority p : Priority.values()) {
            System.out.println(p.getEmoji() + " " + p.name() + " (level=" + p.getLevel() + ", color=" + p.getColor() + ")");
        }

        System.out.println("\n=== Switch pe prioritate ===");
        Priority currentPriority = Priority.HIGH;
        switch (currentPriority) {
            case LOW:
                System.out.println("Prioritate scazuta.");
                break;
            case MEDIUM:
                System.out.println("Prioritate medie.");
                break;
            case HIGH:
                System.out.println("⚠️ Atentie! Prioritate ridicata!");
                break;
            case CRITICAL:
                System.out.println("Critic! Interventie imediata!");
                break;
        }

        System.out.println("\n=== valueOf ===");
        Priority p = Priority.valueOf("HIGH");
        System.out.println("Priority.valueOf(\"HIGH\") = " + p);

        System.out.println("\n=== Comparare enum ===");
        System.out.println("HIGH == HIGH? " + (Priority.HIGH == Priority.HIGH));
        System.out.println("HIGH == LOW? " + (Priority.HIGH == Priority.LOW));

        System.out.println("\n=== name() si ordinal() ===");
        for (Priority priority : Priority.values()) {
            System.out.println(priority.name() + ": name=" + priority.name() + ", ordinal=" + priority.ordinal());
        }
    }
}