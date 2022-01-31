import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import org.junit.jupiter.api.*;

public class SequenceAlignmentTest {

    @Test
    public void example() {
        String a = "kitten";
        String b = "sitting";
        Assertions.assertEquals(3, SequenceAlignment.minSequenceAlignmentCost(a, b));
    }

}
