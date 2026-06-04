package com.pao.laboratory13.exercise1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    enum State {
        INIT, AUTH, OPEN, CLOSED
    }

    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
        }
    }

    private static void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String firstLine = readNonEmptyLine(br);
        if (firstLine == null) {
            return;
        }

        int q = Integer.parseInt(firstLine);
        ProtocolEngine engine = new ProtocolEngine();

        for (int i = 0; i < q; i++) {
            String line = readNonEmptyLine(br);
            if (line == null) {
                return;
            }

            String response = engine.processCommand(line);
            System.out.println(response);
        }
    }

    private static String readNonEmptyLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                return line.trim();
            }
        }
        return null;
    }

    public static class ProtocolEngine {
        private State currentState = State.INIT;
        private int historyCount = 0;
        private String currentUser = "";

        public String processCommand(String rawLine) {
            String[] tokens = rawLine.trim().split("\\s+");
            if (tokens.length == 0 || tokens[0].isEmpty()) {
                return "ERR E_PARSE UNKNOWN_COMMAND";
            }

            String cmd = tokens[0].toUpperCase();


            if (currentState == State.CLOSED && isKnownCommand(cmd)) {
                return "ERR E_STATE CLOSED";
            }

            switch (cmd) {
                case "AUTH":
                    return handleAuth(tokens);
                case "OPEN":
                    return handleOpen(tokens);
                case "SEND":
                    return handleSend(tokens, rawLine);
                case "BROADCAST":
                    return handleBroadcast(tokens, rawLine);
                case "HISTORY":
                    return handleHistory(tokens);
                case "CLOSE":
                    return handleClose(tokens);
                default:
                    return "ERR E_PARSE UNKNOWN_COMMAND";
            }
        }

        private boolean isKnownCommand(String cmd) {
            return cmd.equals("AUTH") || cmd.equals("OPEN") || cmd.equals("SEND") || 
                   cmd.equals("BROADCAST") || cmd.equals("HISTORY") || cmd.equals("CLOSE");
        }

        private String handleAuth(String[] tokens) {
            if (tokens.length < 2) {
                return "ERR E_PARSE AUTH";
            }
            
            currentUser = tokens[1];
            currentState = State.AUTH;
            historyCount = 0; 
            
            return "OK AUTH user=" + currentUser;
        }

        private String handleOpen(String[] tokens) {
            if (tokens.length > 1) {
                return "ERR E_PARSE OPEN";
            }

            if (currentState == State.OPEN) {
                return "ERR E_STATE ALREADY_OPEN";
            }
            if (currentState == State.INIT) {
                return "ERR E_STATE NOT_OPEN";
            }

            currentState = State.OPEN;
            return "OK OPEN";
        }

        private String handleSend(String[] tokens, String rawLine) {
            if (tokens.length < 2) {
                return "ERR E_PARSE SEND";
            }

            if (currentState != State.OPEN) {
                return "ERR E_STATE NOT_OPEN";
            }

            historyCount++;
            return "OK OPEN sent";
        }

        private String handleBroadcast(String[] tokens, String rawLine) {
            if (tokens.length < 2) {
                return "ERR E_PARSE BROADCAST";
            }

            if (currentState != State.OPEN) {
                return "ERR E_STATE NOT_OPEN";
            }

            historyCount++;
            return "OK OPEN broadcast";
        }

        private String handleHistory(String[] tokens) {
            if (tokens.length > 1) {
                return "ERR E_PARSE HISTORY";
            }

            if (currentState != State.OPEN) {
                return "ERR E_STATE NOT_OPEN";
            }

            return "OK OPEN history=" + historyCount;
        }

        private String handleClose(String[] tokens) {
            if (tokens.length > 1) {
                return "ERR E_PARSE CLOSE";
            }

            if (currentState != State.OPEN) {
                return "ERR E_STATE NOT_OPEN";
            }

            currentState = State.CLOSED;
            return "OK CLOSED";
        }
    }
}