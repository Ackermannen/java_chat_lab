import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Wilhelm on 2017-09-20.
 */
public class PARTICIPANTSPDUTest {

    @Test
    public void shouldReadUTF8() throws IOException, checksumException {
        ByteArrayInputStream input = new ByteArrayInputStream(new byte[]{19, 1, 0, 3, (byte) 0xE2, (byte )0x98, (byte) 0x82, 0});
        PDUInterpreter Interpreter = new PDUInterpreter(input);
        PARTICIPANTSPDU PDU = (PARTICIPANTSPDU) Interpreter.getPDU();
        System.out.println(PDU.getOutputMessage());
        assertEquals(PDU.getOutputMessage(), "Users active in chat: \n    ☂\n");
    }
}
