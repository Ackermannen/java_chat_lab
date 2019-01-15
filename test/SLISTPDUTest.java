import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;


public class SLISTPDUTest {

    @Test
    public void shouldCreateCorrectExampleSLISTPDU() throws IOException {
        SLISTPDU SLISTPDUTest = new SLISTPDU(new ByteArrayInputStream(new byte[]{4, 0, 1, 2, 127, 0, 0, 1, 12, 34, 5, 5, 'i', 't', 'c', 'h', 'y' }));
    }

    @Test(expected = IllegalArgumentException.class)//FIXME Nulltermination, fix the skip.
    public void paddingShouldActuallyBeNullTerminated() throws IOException {
        SLISTPDU SLISTPDUTest = new SLISTPDU(new ByteArrayInputStream(new byte[]{4, 1, 1, 2, 127, 0, 0, 1, 12, 34, 5, 5, 'i', 't', 'c', 'h', 'y' }));
    }
}
