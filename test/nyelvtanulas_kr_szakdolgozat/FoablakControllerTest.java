package nyelvtanulas_kr_szakdolgozat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Kremmer Róbert
 */
public class FoablakControllerTest {
    
    FoablakController f;

    @Test
    public void testEloFeldolgozas() {
        // Splittelésben részt vevő karakter többször egymás után
        assertEquals("Pontok: ", ".",f.eloFeldolgozas(".........."));
        
        // Sok felesleges szóköz egy szövegben
        assertEquals("Szóközök: ", "those who get to sleep and wake up late have lower resting brain connectivity ",
                f.eloFeldolgozas("those who      get to      sleep and      wake up     late have       lower    resting brain connectivity     "));
        
        // Szóközök és splittelős karakterek vegyesen
        assertEquals("Vegyes: ", "We .? already.?!.!? know .that there ? ?are ! .? huge negative health consequences",
                f.eloFeldolgozas("We ...????    already.??!.!? know ..that     there ??    ???are !!!  ..? huge negative health consequences"));
        
    }

    @Test
    public void testSzotMegtisztit() {
        assertEquals("Szó megtisztítása", "szó",f.megtisztit("&#/=%(szó/=)(%/(!=!=%"));
    }
    
    @Before
    public void init() {
        f = new FoablakController();
    }

    
}
