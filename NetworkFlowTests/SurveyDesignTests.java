import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.junit.jupiter.api.*;

public class SurveyDesignTests {


//   @Test()
//   @Timeout(100)
//   public void noSolution() {
//     int n = 2;
//     int k = 3;
//     Set<adweblab.networkflow.v_survey_design.explorer.Connection> connections = new HashSet<>();
//     // Person 1 isn't able to review product 3
//     // Person 2 isn't able to review products 2, 3
//     connections.add(new Connection(1, 3));
//     connections.add(new Connection(2, 2));
//     connections.add(new Connection(2, 3));
//     Range[] persons = new Range[n + 1];
//     // Person 1 and 2 want at least 1 products, and max 1 product
//     persons[1] = persons[2] = new Range(1, 1);
//     Range[] products = new Range[k + 1];
//     // 1 person is needed for each product
//     products[1] = products[2] = products[3] = new Range(1, 1);
//     Assertions.assertFalse(SurveyDesign.isReviewPossible(n, k, connections, persons, products));
//   }

    @Test()
    public void exampleOnePersonOneCandy1() {
        int n = 1;
        int k = 1;
        Set<SurveyDesign.Connection> connections = new HashSet<>();
        SurveyDesign.Range[] persons = new SurveyDesign.Range[n + 1];  // Customers min and max they want to review
        persons[1] = new SurveyDesign.Range(1, 1);
        SurveyDesign.Range[] customers = new SurveyDesign.Range[k + 1]; // Products min and max reviews needed
        customers[1] = new SurveyDesign.Range(1, 1);
        Assertions.assertTrue(SurveyDesign.isReviewPossible(n, k, connections, persons, customers));
    }

    @Test()
    public void simpleExample1() {
        int n = 3;
        int k = 3;
        Set<SurveyDesign.Connection> connections = new HashSet<>();
        // Person 1 doesn't like product 3
        // Person 2 doesn't like product 1
        // Person 3 doesn't like products 1, 2
        connections.add(new SurveyDesign.Connection(1, 3));
        connections.add(new SurveyDesign.Connection(2, 1));
        connections.add(new SurveyDesign.Connection(3, 1));
        connections.add(new SurveyDesign.Connection(3, 2));
        SurveyDesign.Range[] persons = new SurveyDesign.Range[n + 1];
        persons[1] = new SurveyDesign.Range(1, 2);
        persons[2] = new SurveyDesign.Range(1, 2);
        persons[3] = new SurveyDesign.Range(1, 1);
        SurveyDesign.Range[] customers = new SurveyDesign.Range[n + 1];
        customers[1] = new SurveyDesign.Range(1, 4);
        customers[2] = new SurveyDesign.Range(2, 3);
        customers[3] = new SurveyDesign.Range(2, 3);
        Assertions.assertTrue(SurveyDesign.isReviewPossible(n, k, connections, persons, customers));
    }

    @Test()
    public void noSolution() {
        int n = 2;
        int k = 3;
        Set<SurveyDesign.Connection> connections = new HashSet<>();
        // Person 1 doesn't like candy 3
        // Person 2 doesn't like candy 2, 3
        connections.add(new SurveyDesign.Connection(1, 3));
        connections.add(new SurveyDesign.Connection(2, 2));
        connections.add(new SurveyDesign.Connection(2, 3));
        int[] personMinCandy = new int[n + 1];
        int[] personMaxCandy = new int[n + 1];
        // Person 1 want 1 candy at least, and can have max 1 candy
        personMinCandy[1] = 1;
        // Person 1 want 1 candy at least, and can have max 1 candy
        personMaxCandy[1] = 1;
        personMinCandy[2] = 1;
        personMaxCandy[2] = 1;
        int[] candyMinApprovals = new int[k + 1];
        int[] candyQuantity = new int[k + 1];
        // 1 person is needed to review the candy and there is a limited stock of 1 candy
        candyMinApprovals[1] = 1;
        // 1 person is needed to review the candy and there is a limited stock of 1 candy
        candyQuantity[1] = 1;
        candyMinApprovals[2] = 1;
        candyQuantity[2] = 1;
        candyMinApprovals[3] = 1;
        candyQuantity[3] = 1;
        Assertions.assertFalse(SurveyDesign.isCandyProofingPossible(n, k, connections, personMinCandy, personMaxCandy, candyMinApprovals, candyQuantity));
    }

    @Test()
    public void exampleOnePersonOneCandy() {
        int n = 1;
        int k = 1;
        Set<SurveyDesign.Connection> connections = new HashSet<>();
        int[] personMinCandy = new int[n + 1];
        int[] personMaxCandy = new int[n + 1];
        personMinCandy[1] = 1;
        personMaxCandy[1] = 1;
        int[] candyMinApprovals = new int[k + 1];
        int[] candyQuantity = new int[k + 1];
        candyMinApprovals[1] = 1;
        candyQuantity[1] = 1;
        Assertions.assertTrue(SurveyDesign.isCandyProofingPossible(n, k, connections, personMinCandy, personMaxCandy, candyMinApprovals, candyQuantity));
    }

    @Test()
    public void simpleExample() {
        int n = 3;
        int k = 3;
        Set<SurveyDesign.Connection> connections = new HashSet<>();
        // Person 1 doesn't like candy 3
        // Person 2 doesn't like candy 1
        // Person 3 doesn't like candy 1, 2
        connections.add(new SurveyDesign.Connection(1, 3));
        connections.add(new SurveyDesign.Connection(2, 1));
        connections.add(new SurveyDesign.Connection(3, 1));
        connections.add(new SurveyDesign.Connection(3, 2));
        int[] personMinCandy = new int[n + 1];
        int[] personMaxCandy = new int[n + 1];
        personMinCandy[1] = 1;
        personMaxCandy[1] = 2;
        personMinCandy[2] = 1;
        personMaxCandy[2] = 2;
        personMinCandy[3] = 1;
        personMaxCandy[3] = 1;
        int[] candyMinApprovals = new int[k + 1];
        int[] candyQuantity = new int[k + 1];
        candyMinApprovals[1] = 1;
        candyQuantity[1] = 4;
        candyMinApprovals[2] = 2;
        candyQuantity[2] = 3;
        candyMinApprovals[3] = 2;
        candyQuantity[3] = 3;
        Assertions.assertTrue(SurveyDesign.isCandyProofingPossible(n, k, connections, personMinCandy, personMaxCandy, candyMinApprovals, candyQuantity));
    }


}
