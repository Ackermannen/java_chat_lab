/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: MESSPDUSender
 * Description: PDU class for creating a PDU of the type MESS to be later sent away to a server.
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Vector;

public class MESSPDUSender extends PDU{

    private int identityLength;
    private int checksum;
    private short messageLength;
    private Date timeStamp;
    private String message;
    private String clientIdentity;

    public MESSPDUSender(String message) throws IOException{
        super(10);

        this.identityLength = 0;

        this.checksum = 0;

        this.messageLength = (short) message.getBytes("UTF-8").length;

        this.timeStamp = new Date((long) 0);

        this.message = message;
    }

    /**
     * Constructs a message of the type MESS according to the protocol.
     * Creates a vector and subsequently adds part into the array, then converts it back into byte[].
     * @return completed byte[]
     */
    public byte[] constructMess() {
        Vector<Byte> byteVector = new Vector<>();

        byteVector.add((byte) getOPNumber());

        byteVector.add((byte) 0x0);

        byteVector.add((byte) identityLength);

        byteVector.add((byte) checksum);

        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(messageLength);
        addBytesToVector(buffer.array(), byteVector);

        buffer = ByteBuffer.allocate(2);
        addBytesToVector(buffer.array(), byteVector);

        buffer = ByteBuffer.allocate(4);
        buffer.putInt((int) timeStamp.getTime());
        addBytesToVector(buffer.array(), byteVector);

        addBytesToVector(message.getBytes(), byteVector);

        for(int i = 0; i < actualPaddedLengthToRead(messageLength); i++) {
            byteVector.add((byte) 0x0);
        }

        byte[] completePDU = vectorToByteArray(byteVector);
        completePDU[3] = (byte) generateChecksum(completePDU);
        return completePDU;
    }
}
