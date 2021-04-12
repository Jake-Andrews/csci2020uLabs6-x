package server;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            var out = new PrintWriter(socket.getOutputStream(), true);
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while (!socket.isClosed() && (line = in.readLine()) != null) {
                System.out.println(line);
                String parts[] = line.split(",");

                if (line.equals("\\q")) {
                    out.println("Bye.");
                    break;
                }
                else if (parts.length == 3) {
                    System.out.println(parts[0]);
                    if (parts[0].equalsIgnoreCase("SEND")){
                        addToBoard(parts[1], parts[2]);
                    }
                    out.println("Message was recieved and added!");
                }
                else {
                    System.out.println("Recieved: " + line + " from client.");
                    out.println(line + " was recieved");
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addToBoard(String userName, String message) {
        Main1.controller.add(userName,message);
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
