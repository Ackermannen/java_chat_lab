import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class PLEAVEPDUTest {

    protected int byteToUnsignedInt(byte b) {
        return b & 0xff;
    }

    @Test
    public void shouldCreateConstructor() throws IOException {
        PLEAVEPDU PLEAVETest = new PLEAVEPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void paddingShouldActuallyBeNullTerminated() throws IOException {
        PLEAVEPDU PLEAVETest = new PLEAVEPDU(new ByteArrayInputStream(new byte[]{16, 6, 1, 1, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
    }

    @Test
    public void getIdentityLengthShouldReturnCorrectIdentityLength() throws IOException{
        PLEAVEPDU PLEAVETest = new PLEAVEPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
        PLEAVETest.getIdentityLength();
        assertEquals(6,6);
    }

    @Test
    public void getTimeStampShouldReturnCorrectTimeStamp() throws IOException{
        PLEAVEPDU PLEAVETest = new PLEAVEPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 1, 3, 3, 7, 'c', 'l', 'i', 'e', 'n', 't'}));
        PLEAVETest.getTimeStamp();
        assertEquals(1337,1337);
    }

    @Test
    public void getIdentityShouldReturnCorrectIdentity() throws IOException{
        PLEAVEPDU PLEAVETest = new PLEAVEPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
        PLEAVETest.getIdentity();
        assertEquals("client", "client");
    }

    @Test
    public void getOutputMessageShouldReturnCorrectOutputMessage() throws IOException{
        PLEAVEPDU PLEAVETest = new PLEAVEPDU(new ByteArrayInputStream(new byte[]{16, 6, 0, 0, 0, 0, 0, 0, 'c', 'l', 'i', 'e', 'n', 't'}));
        System.out.println(PLEAVETest.getOutputMessage());

    }
}
