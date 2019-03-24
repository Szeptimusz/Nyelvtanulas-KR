package nyelvtanulas_kr_szakdolgozat;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Kremmer Róbert
 */
public class AnkiControllerTest {
    AnkiController a;
    
    @Test
    public void testLyukasMondatotKeszit() {
        assertEquals("Lyukas mondat készítése", "......... ",
            a.lyukasMondatotKeszit("emotional", "emotional"));
        
        assertEquals("Lyukas mondat készítése", "The amygdala is strongly linked to ......... ",
            a.lyukasMondatotKeszit("emotional", "The amygdala is strongly linked to emotional"));
        
        
        assertEquals("Lyukas mondat készítése", "The ........ is strongly linked to emotional ",
            a.lyukasMondatotKeszit("amygdala", "The amygdala is, strongly linked to emotional"));

        assertEquals("Lyukas mondat készítése", "One of the joys of train travel is certainly the ....... and when ",
            a.lyukasMondatotKeszit("scenery", "One of the joys of train travel is certainly the scenery, and when"));
    }
    
    @Before
    public void init() {
        a = new AnkiController();
    }
}
