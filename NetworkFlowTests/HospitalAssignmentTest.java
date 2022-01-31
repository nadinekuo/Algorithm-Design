import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.*;


public class HospitalAssignmentTest {


    @Test()
    public void example2Patients1Hospital() {
        int n = 2, k = 1;
        List<HospitalAssignment.Location> patients = new ArrayList<>();
        List<HospitalAssignment.Location> hospitals = new ArrayList<>();
        patients.add(new HospitalAssignment.Location(1, 1));
        patients.add(new HospitalAssignment.Location(1, 2));
        hospitals.add(new HospitalAssignment.Location(1, 3));
        Assertions.assertTrue(HospitalAssignment.isAssignmentPossible(n, k, patients, hospitals, 50));
    }

    @Test()
    public void exampleNoSolution() {
        int n = 2, k = 1;
        List<HospitalAssignment.Location> patients = new ArrayList<>();
        List<HospitalAssignment.Location> hospitals = new ArrayList<>();
        patients.add(new HospitalAssignment.Location(1, 1));
        // too far away from hospital
        patients.add(new HospitalAssignment.Location(1000, 2000));
        hospitals.add(new HospitalAssignment.Location(1, 3));
        Assertions.assertFalse(HospitalAssignment.isAssignmentPossible(n, k, patients, hospitals, 10));
    }
}
