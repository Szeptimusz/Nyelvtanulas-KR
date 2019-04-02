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

        // Szó helyettesítése mondat végén
        assertEquals("A szó a mondat végén:", "The amygdala is strongly linked to ......... ",
            a.lyukasMondatotKeszit("emotional", "The amygdala is strongly linked to emotional"));
        
        // Szó helyettesítése mondat belsejében
        assertEquals("A szó a mondat belsejében:", "The ........ is strongly linked to emotional ",
            a.lyukasMondatotKeszit("amygdala", "The amygdala is, strongly linked to emotional"));

        // Szó helyettesítése közvetlenül vessző előtt
        assertEquals("A szó a vessző előtt:", "One of the joys of train travel is certainly the ....... and when ",
            a.lyukasMondatotKeszit("scenery", "One of the joys of train travel is certainly the scenery, and when"));
        
        // Szó helyettesítése olyan mondatban, amiben a szó nem szerepel
        assertEquals("A szó nincs a mondatban:", "The amygdala is strongly linked to emotional ",
            a.lyukasMondatotKeszit("scenery", "The amygdala is, strongly linked to emotional"));
        
        // Szó helyettesítése olyan mondatban, amiben a szó többször szerepel és több nagy betűs szó is van
        assertEquals("A szó többször szerepel a mondatban:", "Pediatrics ... psychiatry at the ohio state university college of medicine ... a member ",
            a.lyukasMondatotKeszit("and", "Pediatrics and Psychiatry at The Ohio State University College of Medicine and a member")); 
    }
    
    @Before
    public void init() {
        a = new AnkiController();
    }
}
