/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: MESSPDUReader
 * Description: PDU class for reading messages of the type: MESS
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Vector;

public class MESSPDUReader extends PDU{
    private int identityLength;
    private int checksum;
    private short messageLength;
    private int timeStamp;
    private String message;
    private String clientIdentity;

    /**
     * Reads bytes according to protocol for messages of the type MESS.
     * Puts it sequentially into a vector, converts it into byte[] and calculates checksum.
     * @param input Input stream from server to be read as MESS
     * @throws IOException If padding is not nullified
     */
    public MESSPDUReader(InputStream input) throws IOException {
        super(input);

        Vector<Byte> byteVector = new Vector<>();
        DataInputStream dataInput = new DataInputStream(input);

        byteVector.add((byte)getOPNumber());

        if (dataInput.readByte() != 0x0) {
            throw new IOException("Padding is not nullified.");
        }
        byteVector.add((byte) 0x0);

        identityLength = dataInput.readUnsignedByte();
        byteVector.add((byte) identityLength);

        checksum = dataInput.readUnsignedByte();
        byteVector.add((byte) checksum);

        ByteBuffer buffer = ByteBuffer.allocate(2);
        messageLength = (short) dataInput.readUnsignedShort();
        buffer.putShort(messageLength);
        addBytesToVector(buffer.array(), byteVector);

        if (dataInput.readShort() != 0x0) {
            throw new IOException("Padding is not nullified.");
        }
        buffer = ByteBuffer.allocate(2);
        addBytesToVector(buffer.array(), byteVector);

        timeStamp = dataInput.readInt();
        buffer = ByteBuffer.allocate(4);
        buffer.putInt(timeStamp);
        addBytesToVector(buffer.array(), byteVector);

        byte[] messageArray = new byte[messageLength + actualPaddedLengthToRead(messageLength)];
        dataInput.readFully(messageArray);
        message = new String(messageArray).trim();
        addBytesToVector(messageArray, byteVector);

        byte[] identityArray = new byte[this.identityLength + actualPaddedLengthToRead(identityLength)];
        dataInput.readFully(identityArray);
        this.clientIdentity = new String(identityArray).trim();
        addBytesToVector(identityArray, byteVector);

        try {
            compareChecksum(generateChecksum(vectorToByteArray(byteVector)));
        } catch (checksumException e) {
            e.printStackTrace();
        }
    }

    public Date getTimeStamp() {
        return new Date((long) timeStamp*1000);
    }

    public String getOutputMessage() {
        return (getTimeStamp() +" " + clientIdentity + ": " + message);
    }

    private void compareChecksum(byte[] PDU) throws checksumException {
        if (~((PDU[3]) & 0xff + checksum) != 0) {
            throw new checksumException("Checksum does not add up.");
        }
    }

    private void compareChecksum(int otherChecksum) throws checksumException {
        if (otherChecksum != 255) {
            System.out.println(((otherChecksum) & 0xff));
            System.out.println(otherChecksum);
            System.out.println(checksum);
            throw new checksumException("Checksum does not add up.");
        }
    }
}
