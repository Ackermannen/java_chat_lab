/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: PDUInterpreter
 * Description: Class for determining what kind of PDU the InputStream is sending.
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class PDUInterpreter {
    PDU PDU;

    /**
     * Reads the first byte and then unreads it to determine what kind of PDU the server has sent.
     * @param input InputStream to be read
     * @throws IOException if invalid OP code is sent
     * @throws checksumException in case MESSPDUReader detects wrong checksum.
     */
    public PDUInterpreter(InputStream input) throws IOException, checksumException {
        PushbackInputStream sneakInput = new PushbackInputStream(input);
        int OPLooker;
        OPLooker = (sneakInput.read() & 0xff);
        sneakInput.unread(OPLooker);

        switch(OPLooker) {
            case 4:
                PDU = new SLISTPDU(sneakInput);
                break;
            case 10:
                PDU = new MESSPDUReader(sneakInput);
                break;
            case 11:
                PDU = new QUITPDU();
                break;
            case 16:
                PDU = new PJOINPDU(sneakInput);
                break;
            case 17:
                PDU = new PLEAVEPDU(sneakInput);
                break;
            case 19:
                PDU = new PARTICIPANTSPDU(sneakInput);
                break;
            default:
                throw new IOException("Invalid OP code, closing connection");
        }
    }

    public PDU getPDU() {
        return PDU;
    }
}
