/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: QUITPDU (Sender/Reader)
 * Description: PDU class for QUITPDU, is both used for reading
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.IOException;
import java.io.InputStream;

public class QUITPDU extends PDU {
    public QUITPDU() throws IOException {
        super(11);
    }

    public byte[] constructQUIT(){
        return new byte[]{(byte)getOPNumber(),0,0,0};
    }
}
