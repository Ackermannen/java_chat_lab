/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Thread: main, senderThread, receiverThread.
 * Description: Threads for running the chat program.
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
    /**
     * Thread main that starts asks the user for IP and port, and then starts two other threads for chatting.
     * @param args
     * @throws IOException
     * @throws checksumException
     */
    public static void main(String[] args) throws IOException, checksumException {
        String name;
        String choice;
        final Network network = new Network();

        final Scanner scanner = new Scanner(System.in);
        System.out.print("Please pick a name: ");
        name = scanner.nextLine();

        while (true) {
            System.out.println("Would you like to connect to a server[C] or view the" +
                    " list of servers?[L]");
            choice = scanner.nextLine();

            if (choice.equals("C")) {

                try {
                    network.setIPAndPortFromUser(scanner);
                    network.connectToServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    network.sendData(new JOINPDU().constructJOIN(name));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PDUInterpreter interpreter = new PDUInterpreter(network.getStreamIn());
                PARTICIPANTSPDU PDU = (PARTICIPANTSPDU) interpreter.getPDU();
                System.out.println(PDU.getOutputMessage());
                break;
            } else if (choice.equals("L")) {

                try {
                    network.setIPAndPortFromUser(scanner);
                    network.connectToServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    network.sendData(new GETLISTPDU().constructGETLIST());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PDUInterpreter interpreter = new PDUInterpreter(network.getStreamIn());

                SLISTPDU PDU = (SLISTPDU) interpreter.getPDU();
                System.out.println(PDU.getOutputMessage());
            } else {
                System.out.println("INVALID CHOICE, PLEASE CHOOSE AGAIN");
            }
        }

        Thread senderThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    String nextLine = scanner.nextLine();
                    if (nextLine.equals("^1")) {
                        try {
                            network.sendData(new QUITPDU().constructQUIT());
                            System.out.println("Closing connection...");
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            network.sendData(new MESSPDUSender(nextLine).constructMess());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        senderThread.start();

        Thread receiverThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        PDUInterpreter interpreter = new PDUInterpreter(network.getStreamIn());
                            switch (interpreter.getPDU().getOPNumber()) {
                                case 10:
                                    MESSPDUReader message = (MESSPDUReader) interpreter.getPDU();
                                    System.out.println(message.getOutputMessage());
                                    break;
                                case 11:
                                    QUITPDU quitMessage = (QUITPDU) interpreter.getPDU();
                                    System.out.println("Server connection lost.");
                                    System.exit(1);
                                    break;
                                case 16:
                                    PJOINPDU joinAlert = (PJOINPDU) interpreter.getPDU();
                                    System.out.println(joinAlert.getOutputMessage());
                                    break;
                                case 17:
                                    PLEAVEPDU leaveAlert = (PLEAVEPDU) interpreter.getPDU();
                                    System.out.println(leaveAlert.getOutputMessage());
                                    break;
                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }

        };
        receiverThread.start();
    }
}