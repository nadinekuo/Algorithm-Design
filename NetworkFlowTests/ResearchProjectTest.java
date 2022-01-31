import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import org.junit.jupiter.api.*;

public class ResearchProjectTest {

    @Test
    public void exampleThreeStudentsPossible() {
        int n = 3;    // 3 students
        int m = 1;   // 1 supervisor

        boolean[] student_mon = { false, true, true, true };
        boolean[] student_tues = { false, false, false, false };

        boolean[] supervisor_mon = { false, true };
        boolean[] supervisor_tues = { false, true };

        boolean[][] selected = { {}, { false, true },
                                        { false, true },
                                        { false, true } };
        /*
         * This test models the situation where:
         * Students 1,2,3 are available on Mondays
         * Supervisor 1 is available on both days.
         * Every student can be paired to supervisor 1.
         * This is doable as it matches all criteria.
         */
        Assertions.assertTrue(ResearchProject.areThereGroups(n, m, student_mon, student_tues, supervisor_mon, supervisor_tues, selected));
    }

    @Test
    public void exampleSixStudentsPossible() {
        int n = 6;
        int m = 2;
        boolean[] student_mon = { false, true, true, true, false, false, false };
        boolean[] student_tues = { false, false, false, false, true, true, true };

        boolean[] supervisor_mon = { false, true, true };
        boolean[] supervisor_tues = { false, true, false };

        boolean[][] selected = { {}, { false, true, true },
                                     { false, true, true },
                                      { false, true, true },
                                      { false, true, true },
                                      { false, true, true },
                                      { false, true, true } };
        /*
         * This test models the situation where:
         * Students 1,2,3 are available on Mondays and Students 4,5,6 are available on Tuesday.
         * Supervisor 1 is available on both days and supervisor 2 only on Mondays.
         * Every student can be paired to supervisor 1 and supervisor 2.
         * This is doable as we can match students 1,2,3 to supervisor 2 and 4,5,6 to supervisor 1.
         */
        Assertions.assertTrue(ResearchProject.areThereGroups(n, m, student_mon, student_tues, supervisor_mon, supervisor_tues, selected));
    }

    @Test
    public void exampleSixStudentsImpossible() {
        int n = 6;
        int m = 2;
        boolean[] student_mon = { false, true, true, true, true, false, false };
        boolean[] student_tues = { false, false, false, false, false, true, true };
        boolean[] supervisor_mon = { false, false, true };
        boolean[] supervisor_tues = { false, true, false };
        boolean[][] selected = { {}, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true } };
        /*
         * This test models the situation where:
         * Students 1,2,3,4 are available on Mondays and Students 5,6 are available on Tuesday.
         * Supervisor 1 is only available on Tuesdays and supervisor 2 only on Mondays.
         * Every student can be paired to supervisor 1 and supervisor 2.
         * This is impossible as we cannot give the minimum of 3 students to supervisor 2.
         */
        Assertions.assertFalse(ResearchProject.areThereGroups(n, m, student_mon, student_tues, supervisor_mon, supervisor_tues, selected));
    }



}
