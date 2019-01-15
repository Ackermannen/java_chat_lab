/**
 * Created by Wilhelm on 2017-09-15.
 */
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class PDUInterpreterTest {
    @Test
    public void createInterpreter() throws IOException, checksumException {
        byte[] buffer = new byte[]{11,0,0,0};
        ByteArrayInputStream input = new ByteArrayInputStream(buffer);
        PDUInterpreter interpreter = new PDUInterpreter(input);
    }

    @Test
    public void shouldReturnCorrectPDU() throws IOException, checksumException {
        byte[] buffer = new byte[]{11,0,0,0};
        ByteArrayInputStream input = new ByteArrayInputStream(buffer);
        PDUInterpreter interpreter = new PDUInterpreter(input);
        assertEquals(new QUITPDU().getOPNumber(), interpreter.getPDU().getOPNumber());
    }

    @Test (expected = IOException.class)
    public void shouldHateOnWrongOPCode() throws IOException, checksumException {
        byte[] buffer = new byte[]{124,0,0,0};
        ByteArrayInputStream input = new ByteArrayInputStream(buffer);
        PDUInterpreter interpreter = new PDUInterpreter(input);
        assertEquals(new QUITPDU().getOPNumber(), interpreter.getPDU().getOPNumber());
    }
}
