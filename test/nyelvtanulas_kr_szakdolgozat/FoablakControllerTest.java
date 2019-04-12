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
        assertEquals("Szóközök: ", "who get to sleep and",
            f.eloFeldolgozas("who      get to      sleep and"));
        
        // Szóközök és splittelős karakterek vegyesen
        assertEquals("Vegyes: ", "We .? already.?! know",
            f.eloFeldolgozas("We ...????    already.??!!!!  know"));
        
    }

    @Test
    public void testMegtisztit() {
        /* A szöveg megtisztítása az elején és végén az összes 
           (Windows 1250-es kódtáblában lévő) nem támogatott karaktertől,
           a támogatott karakterek ("szó") a String belsejében vannak.
        */
        assertEquals("Nem támogatott karakterek: ", "szó",
            f.megtisztit("!\"#$%&'()*+,-./0123456789:;<=>?@[\\]^_`{"
                    + "|}~€‚ƒ„…†‡ˆ‰‹‘’“”•–—˜™›ˇ˘¤¦§¨©«¬®°±˛´µ¶·¸»˝˙"
                    + "szó!\"#$%&'()*+,-./0123456789:;<=>?@[\\]^_`{|}"
                    + "~€‚ƒ„…†‡ˆ‰‹‘’“”•–—˜™›ˇ˘¤¦§¨©«¬®°±˛´µ¶·¸»˝˙"));
        
        // Egyetlen karakter sem marad a megtisztítás után
        assertEquals("Támogatott karaktert nem tartalmaz: ", "",
            f.megtisztit("\"#$%&'()*+,-./0"));
        
        // A támogatott karakterek a végén vannak
        assertEquals("Támogatott karakter a végén: ", "szó",
            f.megtisztit("\"#$%&'()*+,-./0szó"));
        
        // A támogatott karakterek az elején vannak
        assertEquals("Támogatott karakter az elején: ", "szó",
            f.megtisztit("szó\"#$%&'()*+,-./0"));
    }
    
    @Before
    public void init() {
        f = new FoablakController();
    }


}
