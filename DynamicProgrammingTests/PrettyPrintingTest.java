import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.*;

public class PrettyPrintingTest {

    @Test
    public void example() {
        // L = 42
        // n = 14
        InputStream in = new ByteArrayInputStream(("42\n14\nThe Answer to the Great Question of Life, "
                + "the Universe and Everything is Forty-two.").getBytes());
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("The Answer to the Great Question of Life,");
        expected.add("the Universe and Everything is Forty-two.");
        Assertions.assertEquals(expected, PrettyPrinting.solve(in));
    }



}
