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
        assertEquals("Előfeldolgozás", ".",f.eloFeldolgozas(".........."));
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
