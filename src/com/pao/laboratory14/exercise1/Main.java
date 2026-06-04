package com.pao.laboratory14.exercise1;

import java.util.*;
import java.util.stream.Collector;

public class Main {

    static class Accumulator {
        Map<TipBilet, double[]> data = new HashMap<>();

        public void add(Bilet b) {
            double[] stats = data.computeIfAbsent(b.getTip(), k -> new double[2]);
            stats[0] += 1.0;        
            stats[1] += b.getPret();  
        }

        public Accumulator combine(Accumulator other) {
            other.data.forEach((tip, stats) -> {
                double[] myStats = this.data.computeIfAbsent(tip, k -> new double[2]);
                myStats[0] += stats[0];
                myStats[1] += stats[1];
            });
            return this;
        }

        public RaportVanzari finish() {
            Map<TipBilet, Long> numar = new HashMap<>();
            Map<TipBilet, Double> incasari = new HashMap<>();
            double total = 0;
            long count = 0;

            for (Map.Entry<TipBilet, double[]> entry : data.entrySet()) {
                long c = (long) entry.getValue()[0];
                double sum = entry.getValue()[1];
                numar.put(entry.getKey(), c);
                incasari.put(entry.getKey(), sum);
                total += sum;
                count += c;
            }

            double medie = count == 0 ? 0 : total / count;

            TipBilet popular = numar.entrySet().stream()
                    .max((e1, e2) -> {
                        int cmp = e1.getValue().compareTo(e2.getValue());
                        if (cmp != 0) return cmp;
                        return e2.getKey().name().compareTo(e1.getKey().name());
                    })
                    .map(Map.Entry::getKey)
                    .orElse(null);

            return new RaportVanzari(numar, incasari, total, medie, popular);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        List<Bilet> bilete = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            String ev = sc.next();
            TipBilet tip = TipBilet.valueOf(sc.next());
            double pret = sc.nextDouble();
            bilete.add(new Bilet(id, ev, tip, pret));
        }

        String comanda = sc.next();

        Collector<Bilet, Accumulator, RaportVanzari> raportCollector = Collector.of(
                Accumulator::new,
                Accumulator::add,
                Accumulator::combine,
                Accumulator::finish
        );

        RaportVanzari raport = bilete.stream().collect(raportCollector);

        Arrays.stream(TipBilet.values())
                .filter(t -> raport.getNumarPerTip().containsKey(t))
                .forEach(t -> {
                    System.out.printf(Locale.US, "%s: count=%d incasari=%.2f RON%n",
                            t.name(),
                            raport.getNumarPerTip().get(t),
                            raport.getIncasariPerTip().get(t));
                });

        if ("RAPORT_COMPLET".equals(comanda)) {
            System.out.println("---");
            System.out.printf(Locale.US, "Total: %.2f RON%n", raport.getTotalGlobal());
            System.out.printf(Locale.US, "Medie: %.2f RON%n", raport.getMedieGlobala());
            System.out.println("Cel mai popular: " + 
                (raport.getTipCelMaiPopular() != null ? raport.getTipCelMaiPopular().name() : ""));
        }
    }
}