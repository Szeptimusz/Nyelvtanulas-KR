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
        assertEquals("A szó a mondat végén:", "Strongly linked to ......... ",
            a.lyukasMondatotKeszit("emotional", "Strongly linked to emotional"));
        
        // Szó helyettesítése mondat belsejében
        assertEquals("A szó a mondat belsejében:", "The ........ is strongly ",
            a.lyukasMondatotKeszit("amygdala", "The amygdala is, strongly"));

        // Szó helyettesítése mondat elején
        assertEquals("A szó a mondat belsejében:", "... amygdala is strongly ",
            a.lyukasMondatotKeszit("the", "The amygdala is, strongly"));
        
        // Szó helyettesítése közvetlenül vessző előtt
        assertEquals("A szó a vessző előtt:", "Is certainly the ....... and when ",
            a.lyukasMondatotKeszit("scenery", "Is certainly the scenery, and when"));
        
        // Szó helyettesítése olyan mondatban, amiben a szó nem szerepel
        assertEquals("A szó nincs a mondatban:", "The amygdala is ",
            a.lyukasMondatotKeszit("scenery", "The amygdala is"));
        
        /* Szó helyettesítése olyan mondatban, amiben a szó többször szerepel és
        több nagy betűs szó is van benne */
        assertEquals("A szó többször szerepel a mondatban:", 
            "Pediatrics ... psychiatry at the ohio state university college of medicine ... ",
            a.lyukasMondatotKeszit("and", "Pediatrics and Psychiatry at "
                    + "The Ohio State University College of Medicine and")); 
    }
    
    @Before
    public void init() {
        a = new AnkiController();
    }
}
