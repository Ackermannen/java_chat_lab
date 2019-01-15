import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CSTrash {

    public static void main(String[] args){

        if(args.length < 1){
            System.err.println("Usage: PORT");
        }

        try {
            int port = Integer.parseInt(args[0]);
            boolean shouldRun = true;

            ServerSocket acceptor = new ServerSocket(port);
            System.out.println();

            Socket clientSocket = acceptor.accept();

            InputStream clientInput = clientSocket.getInputStream();
            OutputStream clientOutput = clientSocket.getOutputStream();

            if (clientInput.read() != 12) {
                System.out.println("Wrong OP");
                System.exit(-1);
            }

            int identityLength = clientInput.read() & 0xff;

            clientInput.read();
            clientInput.read();

            int bytesToRead = (identityLength % 4) == 0 ? identityLength :
                    (identityLength + (4 - identityLength % 4));

            for (int i = 0; i < bytesToRead; i++)
                clientInput.read();

            //Låtsas skicka en giltig participant för att sedan skicka resten
            clientOutput.write(new byte[]{19, 0, 0, 0});
            clientOutput.write(new byte[]{
                    0xa,
                    1,
                    1,
                    5,
                    0, 0,
                    0, 0,
                    0, 0, 0, 0,
                    0x41, 0, 0, 0});
            //Fel checksumma?

            //Hantera olika fel
        }catch (IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

