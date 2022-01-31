import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class LongestCommonPrefixTest {

    @Test
    public void example() {
        int n = 2;
        String[] encodings = new String[n + 1];
        encodings[1] = "Hello World";
        encodings[2] = "Hello Weasley";
        Assertions.assertEquals("Hello W", LongestCommonPrefix.longestPrefix(n, encodings));
    }


    @Test
    public void example2Empty() {
        int n = 2;
        String[] encodings = new String[n + 1];
        encodings[1] = "";
        encodings[2] = "";
        Assertions.assertEquals("", LongestCommonPrefix.longestPrefix(n, encodings));
    }

    @Test
    public void example1Empty() {
        int n = 1;
        String[] encodings = new String[n + 1];
        encodings[1] = "";
        Assertions.assertEquals("", LongestCommonPrefix.longestPrefix(n, encodings));
    }


    @Test
    public void exampleFullStringMatches() {
        int n = 2;
        String[] encodings = new String[n + 1];
        encodings[1] = "Hello World";
        encodings[2] = "Hello World";
        Assertions.assertEquals("Hello World", LongestCommonPrefix.longestPrefix(n, encodings));
    }

    @Test
    public void secondExample() {
        int n = 3;
        String[] encodings = new String[n + 1];
        encodings[1] = "Hello World";
        encodings[2] = "Hello Weasley";
        encodings[3] = "Hiya mate!";
        Assertions.assertEquals("H", LongestCommonPrefix.longestPrefix(n, encodings));
    }

    @Test
    public void exampleBinary() {
        int n = 7;
        String[] encodings = new String[n + 1];
        encodings[1] = "010101";
        encodings[2] = "00101010";
        encodings[3] = "00010101";
        encodings[4] = "10101";
        encodings[5] = "00001";
        encodings[6] = "00000";
        encodings[7] = "10111";
        Assertions.assertEquals("", LongestCommonPrefix.longestPrefix(n, encodings));
    }


}
