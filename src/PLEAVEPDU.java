/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: PLEAVEPDU
 * Description: PDU class for reading messages of the type PLEAVE.
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;

public class PLEAVEPDU extends PDU {
    private int identityLength;
    private int timeStamp;
    private String identity;

    /**
     * Reads PDU as
     * @param input inputstream to be read
     * @throws IOException if padding is not nullified
     */
    public PLEAVEPDU(InputStream input) throws IOException {

        super(input);

        DataInputStream dataInput = new DataInputStream(input);

        this.identityLength = dataInput.readUnsignedByte();

        if (dataInput.readShort() != 0x0) {
            throw new IOException("Padding is not nullified.");
        }

        this.timeStamp = dataInput.readInt();

        byte[] identityArray = new byte[identityLength + actualPaddedLengthToRead(identityLength)];
        dataInput.readFully(identityArray);
        this.identity = new String(identityArray);
    }

    public int getIdentityLength() {
        return identityLength;
    }

    public Date getTimeStamp() {
        return new Date((long) timeStamp*1000);
    }

    public String getIdentity() {
        return identity;
    }

    public String getOutputMessage() {
        return ("User '" + identity + "' has left the server at " + getTimeStamp());
    }
}
