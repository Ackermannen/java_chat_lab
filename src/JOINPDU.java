/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: JOINPDU (SENDER PDU)
 * Description: PDU class for creating a JOINPDU message to a server.
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class JOINPDU extends PDU{

    public JOINPDU() throws IOException {
        super(12);
    }

    /**
     * Constructor that creates a JOIN message by adding byte after byte to a
     * vector which later converts it to a byte array
     * @param clientIdentity - Clients name
     * @return byte array of the join PDU
     * @throws UnsupportedEncodingException if unable to getBytes for UTF-8
     */
    public byte[] constructJOIN(String clientIdentity) throws UnsupportedEncodingException {
        Vector<Byte> byteVector = new Vector<>();
        byteVector.add((byte)getOPNumber());
        byteVector.add((byte)clientIdentity.getBytes("UTF-8").length);
        byteVector.add((byte)0x0);
        byteVector.add((byte)0x0);
        addBytesToVector(clientIdentity.getBytes(), byteVector);

        for (int i=0; i < actualPaddedLengthToRead(clientIdentity.getBytes("UTF-8").length); i++) {
            byteVector.add((byte)0x0);
        }

        return vectorToByteArray(byteVector);
    }
}
