import static org.junit.Assert.*;
import org.junit.Test;

public class hamberguesasTest {

    @Test
    public void testBasicCase() {
        hamberguesas h = new hamberguesas();
        String result = h.calculaMejor(3, 5, 17);
        // Just check output format and values
        assertTrue(result.startsWith("a= "));
        assertTrue(result.contains("b="));
    }

    @Test
    public void testEqualNandM() {
        hamberguesas h = new hamberguesas();
        String result = h.calculaMejor(4, 4, 16);
        assertEquals("a= 4b= 4", result);
    }

    @Test
    public void testTisZero() {
        hamberguesas h = new hamberguesas();
        String result = h.calculaMejor(3, 5, 0);
        assertEquals("a= 0b= 0", result);
    }

    @Test
    public void testNisOne() {
        hamberguesas h = new hamberguesas();
        String result = h.calculaMejor(1, 5, 10);
        assertEquals("a= 10b= 2", result);
    }

    @Test
    public void testMisOne() {
        hamberguesas h = new hamberguesas();
        String result = h.calculaMejor(3, 1, 7);
        assertEquals("a= 2b= 7", result);
    }

    @Test
    public void testLargeT() {
        hamberguesas h = new hamberguesas();
        String result = h.calculaMejor(2, 3, 100);
        assertTrue(result.startsWith("a= "));
        assertTrue(result.contains("b="));
    }
}