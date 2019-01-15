/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: ServerListObject
 * Description: Class for compiling data for one server
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.net.InetAddress;

public class ServerListObject {

    private InetAddress serverAddress;
    private int serverPort;
    private int clients;
    private String serverName;

    public ServerListObject(InetAddress serverAddress, int serverPort, int clients, String serverName){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.clients = clients;
        this.serverName = serverName;
    }

    public InetAddress getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getClients() {
        return clients;
    }

    public String getServerName() {
        return serverName;
    }
}
