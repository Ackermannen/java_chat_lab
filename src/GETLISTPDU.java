/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: GETLISTPDU (SENDER PDU)
 * Description: PDU class for creating a GETLIST message to later be sent away to a server.
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.IOException;
import java.io.InputStream;

public class GETLISTPDU extends PDU{

    public GETLISTPDU() throws IOException {
        super(3);
    }

    public byte[] constructGETLIST(){
        byte[] constructedGetList = new byte[]{ (byte) getOPNumber(),0,0,0};
        return constructedGetList;
    }
}
