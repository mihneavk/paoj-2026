package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();

        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.next();
                TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

                ByteBuffer idBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id);
                dos.write(idBuffer.array());

                ByteBuffer sumaBuffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma);
                dos.write(sumaBuffer.array());

                byte[] dataBytes = new byte[10];
                Arrays.fill(dataBytes, (byte) ' ');
                byte[] strBytes = data.getBytes();
                System.arraycopy(strBytes, 0, dataBytes, 0, Math.min(strBytes.length, 10));
                dos.write(dataBytes);

                dos.writeByte(tip == TipTranzactie.CREDIT ? 0 : 1);
                dos.writeByte(0);
                dos.write(new byte[8]);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String command = scanner.next();

                if (command.equals("READ")) {
                    int idx = scanner.nextInt();
                    printRecord(raf, idx);
                } else if (command.equals("UPDATE")) {
                    int idx = scanner.nextInt();
                    String statusStr = scanner.next();
                    int statusByte = statusStr.equals("PROCESSED") ? 1 : statusStr.equals("REJECTED") ? 2 : 0;
                    
                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.writeByte(statusByte);
                    System.out.println("Updated [" + idx + "]: " + statusStr);
                } else if (command.equals("PRINT_ALL")) {
                    int totalRecords = (int) (raf.length() / RECORD_SIZE);
                    for (int i = 0; i < totalRecords; i++) {
                        printRecord(raf, i);
                    }
                }
            }
        }

        scanner.close();
    }

    private static void printRecord(RandomAccessFile raf, int idx) throws IOException {
        raf.seek((long) idx * RECORD_SIZE);
        byte[] record = new byte[RECORD_SIZE];
        raf.readFully(record);

        ByteBuffer buffer = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);
        int id = buffer.getInt(0);
        double suma = buffer.getDouble(4);

        byte[] dataBytes = new byte[10];
        System.arraycopy(record, 12, dataBytes, 0, 10);
        String data = new String(dataBytes).trim();

        String tip = record[22] == 0 ? "CREDIT" : "DEBIT";
        String status = record[23] == 0 ? "PENDING" : record[23] == 1 ? "PROCESSED" : "REJECTED";

        System.out.printf(Locale.US, "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                idx, id, data, tip, suma, status);
    }
}