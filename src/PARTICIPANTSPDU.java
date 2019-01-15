/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: PARTICIPANTSPDU (Reader)
 * Description: PDU class for reading messages of the type PARTICIPANTS
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class PARTICIPANTSPDU extends PDU{
    private int identitiesAmount;
    private int identityLength;
    private Stack<String> userStack;

    /**
     * Reads bytes like a PARTICIPANTS message.
     * @param input InputStream it reads from
     * @throws IOException if unable to read fully from stream
     */
    public PARTICIPANTSPDU(InputStream input) throws IOException {
        super(input);

        userStack = new Stack<>();
        DataInputStream dataInput = new DataInputStream(input);

        this.identitiesAmount = dataInput.readUnsignedByte();

        this.identityLength = dataInput.readUnsignedShort();

        byte[] identities = new byte[this.identityLength];
        dataInput.readFully(identities);

        int paddingToRead = actualPaddedLengthToRead(identityLength);
        byte[] paddingArray = new byte[paddingToRead];
        dataInput.readFully(paddingArray);

        String[] str = new String(identities).split("\0");

        for (int i = 0; i < str.length; i++) {
            userStack.push(str[i].trim());
        }
    }

    public int getIdentitiesAmount() {
        return identitiesAmount;
    }

    public long getIdentityLength() {
        return identityLength;
    }

    public String getOutputMessage() {
        Stack localStack = (Stack) userStack.clone();
        String output = "Users active in chat: \n";
        while(!localStack.isEmpty()) {
            output += ("    " + localStack.pop() + "\n");
        }
        return output;
    }
}
