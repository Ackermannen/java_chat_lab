import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.TestCase.assertEquals;

public class PJOINPDUTest {

    protected int byteToUnsignedInt(byte b) {
        return b & 0xff;
    }

    @Test
    public void shouldCreateConstructor() throws IOException {
        PJOINPDU PJOINTest = new PJOINPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void paddingShouldActuallyBeNullTerminated() throws IOException {
        PJOINPDU PJOINTest = new PJOINPDU(new ByteArrayInputStream(new byte[]{16, 6, 1, 1, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
    }

    @Test
    public void getIdentityLengthShouldReturnCorrectIdentityLength() throws IOException{
        PJOINPDU PJOINTest = new PJOINPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
        PJOINTest.getIdentityLength();
        assertEquals(6,6);
    }

    @Test
    public void getTimeStampShouldReturnCorrectTimeStamp() throws IOException{
        PJOINPDU PJOINTest = new PJOINPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 1, 3, 3, 7, 'c', 'l', 'i', 'e', 'n', 't'}));
        PJOINTest.getTimeStamp();
        assertEquals(1337,1337);
    }

    @Test
    public void getIdentityShouldReturnCorrectIdentity() throws IOException{
        PJOINPDU PJOINTest = new PJOINPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
        PJOINTest.getIdentity();
        assertEquals("client", "client");
    }

    @Test
    public void getOutputMessageShouldReturnCorrectOutputMessage() throws IOException{
        PJOINPDU PJOINTest = new PJOINPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
        System.out.println(PJOINTest.getOutputMessage());

    }
}
