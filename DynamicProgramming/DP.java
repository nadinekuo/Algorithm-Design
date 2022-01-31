import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class DP {



    public static void main(String[] args) {


    }


    public static double optimalTrade(int t, double[] r) {

        double[] memE = new double[t + 1];
        double[] memB = new double[t + 1];
        memE[0] = 0;
        memB[0] = 0.1;

        for (int i = 1; i <= t; i++) {
            memE[i] = Math.max(memE[i-1], memB[i-1] * 0.95 * r[t]);
            memB[i] = Math.max(memB[i-1], (memE[i-1] - 5) / r[t]);
        }
        return memE[t];

    }



}
