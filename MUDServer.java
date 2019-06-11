import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

// MUDServer.java
// Accepts connections from multiple clients
public class MUDServer {

    private static Dungeon d; // instance of dungeon

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("USAGE: java MUDServer [dimension of world] [max number of threads]");
            return;
        }
        d = new Dungeon(Integer.parseInt(args[0]));
        System.out.println("===SERVER IS RUNNING===");
        ExecutorService pool = Executors.newFixedThreadPool(Integer.parseInt(args[1])); // allow a max number of connections
        try (ServerSocket listener = new ServerSocket(59001)) {
            while (true) {
                pool.execute(new UserHandler(listener.accept())); // create a new thread for a new connection
            }
        }
    }

    private static class UserHandler implements Runnable {
        private String name;
        private Socket s;
        private Coordinates c;
        private Scanner in; // input from client
        private PrintWriter out; // output to client

        public UserHandler(Socket s) {
            this.s = s;
            do {
                c = new Coordinates(d);
            } while (d.getRoom(c).isSolid()); // keep creating spawn coordinates until user can spawn in a transparent room
        }

        public synchronized void run() { // mutex
            try {
                in = new Scanner(s.getInputStream());
                out = new PrintWriter(s.getOutputStream(), true);

                // Accept a valid name. If the name is invalid, prompt the client for another name.
                while (true) {
                    out.println("PROMPT");
                    name = in.nextLine();
                    if (name == null) {
                        return;
                    }
                    //synchronized (d.getUsers()) {
                        if (!(name.isEmpty() || Pattern.compile("[()/]").matcher(name).find() ||
                                d.getNames().contains(name))) {
                            System.out.println(name + " is joining");
                            for (PrintWriter writer : d.getWriters()) {
                                writer.println("MESSAGE " + name + " has joined the session.");
                            }
                            d.addUser(name, out); // add user to the dungeon
                            d.getRoom(c).addUser(name, out); // add user to the spawn room
                            break;
                        }
                    //}
                }
                out.println("CONNECTED " + name);
                // Repeatedly process client input and output pertinent information
                while (true) {
                    String input = in.nextLine();
                    String[] tokens; // used to store commands split up by delimiter /
                    if (input.toLowerCase().startsWith("/quit")) {
                        return;
                    }
                    if (input.toLowerCase().startsWith("/yell")) {
                        tokens = input.split("/");
                        if(tokens.length != 3) {
                            out.println("MESSAGE USAGE: /yell/<dialog>");
                        }
                        else {
                            for (PrintWriter writer : d.getWriters()) {
                                writer.println("MESSAGE " + name + ": " + tokens[2]);
                            }
                        }
                    }
                    else if (input.toLowerCase().startsWith("/tell")) {
                        tokens = input.split("/");
                        if(tokens.length != 4) {
                            out.println("MESSAGE USAGE: /tell/<person_name>/<dialog>");
                        }
                        else {
                            if(d.getRoom(c).getNames().contains(tokens[2])) {
                                d.getRoom(c).getWriter(tokens[2]).println("MESSAGE " + name + ": " + tokens[3]);
                            }
                            else {
                                out.println("MESSAGE " + tokens[2] + " is not in this room!");
                            }
                        }
                    }
                    else if (input.toLowerCase().startsWith("/say")) {
                        tokens = input.split("/");
                        if(tokens.length != 3) {
                            out.println("MESSAGE USAGE: /say/<dialog>");
                        }
                        else {
                            for (PrintWriter writer : d.getRoom(c).getWriters()) {
                                writer.println("MESSAGE " + name + ": " + tokens[2]);
                            }
                        }
                    }
                    else if (input.toLowerCase().startsWith("/east") || input.toLowerCase().startsWith("/west")
                            || input.toLowerCase().startsWith("/north") || input.toLowerCase().startsWith("/south")
                            || input.toLowerCase().startsWith("/up") || input.toLowerCase().startsWith("/down")) {
                        tokens = input.split("/");
                        if (move(tokens[1])) {
                            out.println("MESSAGE " + d.getRoom(c).getDescription());
                        }
                        else { // if the desired room is solid
                            out.println("MESSAGE You are trying to enter a solid room!"); 
                        }
                    }
                    else {
                        out.println("MESSAGE USAGE: Invalid command!");
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
            finally {
                if (name != null) {
                    System.out.println(name + " is leaving.");
                    d.getRoom(c).removeUser(name); // remove user from current room
                    d.removeUser(name); // remove user from dungeon
                    for (PrintWriter writer : d.getWriters()) {
                        writer.println("MESSAGE " + name + " has left the session.");
                    }
                }
                try {
                    s.close();
                }
                catch (IOException e) {
                }
            }
        }

        // Carry out a movement in a given direction. If a client oversteps the boundaries of the dungeon,
        // they are moved across the dungeon rather than being denied a move.
        private boolean move(String direction) {
            if (d.getRoom(c.getNewCoordinates(direction)).isSolid()) {
                return false; // do not move if the desired room is solid
            }
            d.getRoom(c).removeUser(name); // remove user from the current room
            // notify other users in the room that this user has left
            for (PrintWriter writer : d.getRoom(c).getWriters()) {
                if (writer != out) {
                    writer.println("MESSAGE " + name + " has exited this room.");
                }
            }
            c = c.getNewCoordinates(direction); // update coordinates
            d.getRoom(c).addUser(name, out); // add user to new room
            //notify other users in the new room that the user has entered
            for (PrintWriter writer : d.getRoom(c).getWriters()) {
                if (writer != out) {
                    writer.println("MESSAGE " + name + " has entered this room.");
                }
            }
            return true;
        }
    }
}