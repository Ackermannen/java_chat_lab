/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: PDU
 * Description: Master class for all types of PDU regardless of sending or reading.
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Vector;

public abstract class PDU {
    private int OPNumber;

    public PDU(InputStream input) throws IOException {
        DataInputStream dataInput = new DataInputStream(input);
        OPNumber = dataInput.readUnsignedByte();
    }

    public PDU(int OP) throws IOException {
        OPNumber = (OP) & 0xff;
    }

    int getOPNumber() {
        return OPNumber;
    }

    protected int byteToUnsignedInt(byte b) {
        return b & 0xff;
    }

    protected Date getCurrentDate(long time) {
        time = time*1000;
        Date date = new Date(time);
        return date;
    }

    /**
     * Adds each unsigned value of a byte array as a sum and subtracts it with 255 if larger or equal to 255.
     * @param b byte array
     * @return final checksum
     */
    int generateChecksum(byte[] b){
        int sum = 0;
        for (byte aB : b) {
            sum += byteToUnsignedInt(aB);
            if (sum >= 255) {
                sum = sum-255;
            }
        }
        return (~sum) & 0xff;
    }

    /**
     * If padded to the next four bytes, you can use this method to calculate how many extra bytes you have to read.
     * @param length length of some object without padding.
     * @return integer for amount of extra bytes to read
     */
    int actualPaddedLengthToRead(int length) {
        switch(length%4) {
            case 0:
                return 0;
            case 1:
                return 3;
            case 2:
                return 2;
            case 3:
                return 1;
            default:
                return 0;
        }
    }

    protected void addBytesToVector(byte[] b, Vector<Byte> v) {
        for (byte aB : b) {
            v.add(aB);
        }
    }

    protected byte[] vectorToByteArray(Vector<Byte> byteVector) {
        byte[] b = new byte[byteVector.size()];

        for (int i = 0; i < b.length; i++) {
            b[i] = byteVector.get(i);
        }
        return b;
    }
}
