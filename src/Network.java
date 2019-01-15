/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: Network
 * Description: Class to
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Network {
    private OutputStream streamOut;
    private InputStream streamIn;
    private InetAddress address;
    private int port;
    private Socket socket;

    /**
     * Establishes a connection with the server, and saves the socket InputStream and OutputStream
     * @throws InterruptedException If Thread can't sleep
     * @throws IOException If socket connection fails
     */
    public void connectToServer() throws InterruptedException, IOException {

        int counter = 1;
        socket = null;

        while (true){
            try {
                counter++;
                if (counter == 10){
                    System.out.println("Connection failed after 10 retries, please select a new IP");
                    return;
                }
                Thread.sleep(10*counter*counter);

                socket = new Socket(address, port);
            } catch (IOException e) {
                e.printStackTrace();
            }
            streamIn = socket.getInputStream();
            streamOut = socket.getOutputStream();
            break;
        }

        if (socket != null) {
            streamOut = new DataOutputStream(socket.getOutputStream());
        }
    }

    public void sendData(byte[] bytes) throws IOException {
        streamOut.write(bytes);
        streamOut.flush();
    }

    public OutputStream getStreamOut() {
        return streamOut;
    }

    public InputStream getStreamIn() {
        return streamIn;
    }

    public void setIPAndPortFromUser(Scanner scanner) {
        while(true) {
            try {
                System.out.print("Please enter the IP address(0.0.0.0): ");
                address = InetAddress.getByName(scanner.nextLine());
                break;
            } catch (UnknownHostException e) {
                System.out.println("Unknown host, please enter a correct IP-address");
            }
        }

        while(true) {
            try {
                System.out.print("Please enter the port: ");
                port = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid port format, please try again");
            }
        }
    }

    public void closeConnection() throws IOException {
        socket.close();
    }
}

