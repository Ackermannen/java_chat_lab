import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.Assert.*;

public class MESSPDUTest {
    @Test
    public void MESSOutputShouldBeSame() throws IOException, checksumException {

        byte[] data = new byte[]{11, 0, 1, (byte) (255-208), 0, 1,
                0, 0, 0, 0, 0, 0, 'a', 0, 0, 0, 'b', 0, 0, 0};
        ByteArrayInputStream input = new ByteArrayInputStream(data);

        MESSPDUReader PDU = new MESSPDUReader(input);

        assertEquals("b: a\n", PDU.getOutputMessage());


    }

    @Test (expected = IOException.class)
    public void ShouldHateOnWrongPadding() throws IOException, checksumException {

        byte[] data = new byte[]{11, 1, 1, (byte) (255-208), 0, 1,
                0, 0, 0, 0, 0, 0, 'a', 'b', 0, 0};
        ByteArrayInputStream input = new ByteArrayInputStream(data);

        MESSPDUReader PDU = new MESSPDUReader(input);
    }

    @Test (expected = IOException.class)
    public void ShouldHateOnWrongIdentityLength() throws IOException, checksumException {

        byte[] data = new byte[]{11, 0, 2, (byte) (255-208), 0, 1,
                0, 0, 0, 0, 0, 0, 'a', 'b', 0, 0};
        ByteArrayInputStream input = new ByteArrayInputStream(data);

        MESSPDUReader PDU = new MESSPDUReader(input);
    }

}
