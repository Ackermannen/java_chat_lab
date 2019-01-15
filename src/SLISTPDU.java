/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: SLISTPDU (Reader)
 * Description: PDU class for reading messages of type PDU
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SLISTPDU extends PDU{

    private int servers;
    private ArrayList<ServerListObject> serverList;

    /**
     * Reads PDU as a SLIST message
     * @param input inputstream to be read
     * @throws IOException if padding is not nullified
     */
    public SLISTPDU(InputStream input) throws IOException{
        super(input);

        DataInputStream dataInput = new DataInputStream(input);
        serverList = new ArrayList<>();

        if (dataInput.readByte() != 0x0) {
            throw new IOException("Padding is not nullified.");
        }

        this.servers = dataInput.readUnsignedShort();

        for(int i = 0; i < servers; i++) {
            ServerListObject serverObject;

            InetAddress serverAddress = readIP(dataInput);

            int serverPort = dataInput.readUnsignedShort();

            int clients = dataInput.readUnsignedByte();

            int serverNameLength = dataInput.readUnsignedByte();

            byte[] lengthArray = new byte[serverNameLength + actualPaddedLengthToRead(serverNameLength)];
            dataInput.readFully(lengthArray);
            String serverName = new String(lengthArray).trim();

            serverObject = new ServerListObject(serverAddress, serverPort, clients, serverName);
            serverList.add(serverObject);
        }
    }

    private InetAddress readIP(DataInputStream input) throws UnknownHostException {
        byte[] buffer = new byte[4];
        for (int i = 0; i < 4; i++) {
            try {
                buffer[i] = (byte) input.readUnsignedByte();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return InetAddress.getByAddress(buffer);
    }

    public String getOutputMessage() {
        StringBuilder output = new StringBuilder("Active sessions: \n");
        ServerListObject currentObject;

        for(int i = 0; i < servers; i++){
            currentObject = serverList.get(i);
            output.append(("\n") + ("Server IP: ") + currentObject.getServerAddress().toString() + ("\nPort: "));
            output.append(String.valueOf(currentObject.getServerPort()) + ("\nClients connected: "));
            output.append(String.valueOf(currentObject.getClients()) + ("\nServer name: "));
            output.append(currentObject.getServerName() + ("\n"));
        }
        return String.valueOf(output);
    }
}
