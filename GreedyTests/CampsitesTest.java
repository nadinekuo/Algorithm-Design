import org.junit.Test;
import static org.junit.Assert.*;

public class CampsitesTest {

    @Test
    public void testNoCampsitesPossible() {

        Campsites.Campsite c1 = new Campsites.Campsite("a", 0);
        Campsites.Campsite c2 = new Campsites.Campsite("b", 15);  // first campsite can never be reached!
        Campsites.Campsite c3 = new Campsites.Campsite("c", 32);
        Campsites.Campsite[] camps = new Campsites.Campsite[]{c1, c2, c3};

        assertEquals(-1, Campsites.amountCampsitesNeeded(camps, 5));

    }

    @Test
    public void testDestinationUnreachable() {  // We only passed 1 campsite

        Campsites.Campsite c1 = new Campsites.Campsite("a", 0);
        Campsites.Campsite c2 = new Campsites.Campsite("b", 4);  // first campsite can never be reached!
        Campsites.Campsite c3 = new Campsites.Campsite("c", 16);  // 16 > 10+4 so can never be reached!
        Campsites.Campsite[] camps = new Campsites.Campsite[]{c1, c2, c3};

        assertEquals(1, Campsites.amountCampsitesNeeded(camps, 10));

    }



    // EXcluding first and final campsite!!!
    @Test
    public void test1CampsiteNeeded() {

        Campsites.Campsite c1 = new Campsites.Campsite("a", 0);
        Campsites.Campsite c2 = new Campsites.Campsite("b", 4);
        Campsites.Campsite c3 = new Campsites.Campsite("c", 16);
        Campsites.Campsite[] camps = new Campsites.Campsite[]{c1, c2, c3};

        assertEquals(1, Campsites.amountCampsitesNeeded(camps, 10));
    }


    @Test
    public void test1CampsitesNeeded1Skipped() {

        Campsites.Campsite c1 = new Campsites.Campsite("a", 0);
        Campsites.Campsite c2 = new Campsites.Campsite("b", 4);  // skipped
        Campsites.Campsite c3 = new Campsites.Campsite("c", 10);
        Campsites.Campsite c4 = new Campsites.Campsite("d", 16);
        Campsites.Campsite[] camps = new Campsites.Campsite[]{c1, c2, c3, c4};

        assertEquals(1, Campsites.amountCampsitesNeeded(camps, 10));
    }

    @Test
    public void test2CampsitesNeeded() {

        Campsites.Campsite c1 = new Campsites.Campsite("a", 0);
        Campsites.Campsite c2 = new Campsites.Campsite("b", 8);
        Campsites.Campsite c3 = new Campsites.Campsite("c", 18);
        Campsites.Campsite c4 = new Campsites.Campsite("d", 28);
        Campsites.Campsite[] camps = new Campsites.Campsite[]{c1, c2, c3, c4};

        assertEquals(2, Campsites.amountCampsitesNeeded(camps, 10));
    }


}
