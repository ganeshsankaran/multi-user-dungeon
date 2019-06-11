import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// MUDClient.java
// Client program that connects to MUDServer via the server IP address

public class MUDClient {

    String serverAddress;
    Scanner in; // input from the server
    PrintWriter out; // output to the server
    JFrame frame = new JFrame("Multi-User Dungeon");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);

    public MUDClient(String serverAddress) {
        this.serverAddress = serverAddress;

        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();

        textField.addActionListener((ActionEvent e) -> {
            out.println(textField.getText());
            textField.setText("");
        });
    }

    // Submit a name to the server
    private void run() throws IOException {
        try {
            Socket socket = new Socket(serverAddress, 59001);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.startsWith("PROMPT")) { // if server is requesting a name, output the name
                    out.println(getName());
                }
                else if (line.startsWith("CONNECTED")) { // allow user to start entering commands once name is accepted
                    this.frame.setTitle("Multi-User Dungeon - " + line.substring(10));
                    textField.setEditable(true);
                }
                else if (line.startsWith("MESSAGE")) { // display message from server
                    messageArea.append(line.substring(8) + "\n");
                }
            }
        } finally {
            frame.setVisible(false);
            frame.dispose();
        }
    }

    private String getName() {
        return JOptionPane.showInputDialog(frame,
                "Empty names and names containing ( ) / are not allowed.",
                "Choose a Name",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("USAGE: java MUDClient [Server IP Address]");
            return;
        }
        MUDClient client = new MUDClient(args[0]);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}