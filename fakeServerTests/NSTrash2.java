import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NSTrash2 {
    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt("6666");

        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();

        InputStream clientInput = clientSocket.getInputStream();
        OutputStream clientOutput = clientSocket.getOutputStream();

        if(clientInput.read() != 3) {
            System.out.println("WRONG OP CODE");
            System.exit(1);
        }

        clientInput.read();
        clientInput.read();
        clientInput.read();

        clientOutput.write(new byte[]{4, 0x0, 0x0, 0x0});
    }
}
