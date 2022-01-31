import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SelectingProjectTest {


    @Test
    public void example1Proj2Req() {
        ProjectSelection.Project project1 = new ProjectSelection.Project(1, 5, 0);  // id, recvenue, cost
        ProjectSelection.Project project2 = new ProjectSelection.Project(2, 0, 1);
        ProjectSelection.Project project3 = new ProjectSelection.Project(3, 0, 2);
        project1.addRequirement(2);
        project1.addRequirement(3);
        List<ProjectSelection.Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        // Feasible project set: 1, 2, 3
        Assertions.assertEquals(2, ProjectSelection.maximumProjects(projects));
    }

    @Test
    public void example2() {
        ProjectSelection.Project project1 = new ProjectSelection.Project(1, 5, 0);
        ProjectSelection.Project project2 = new ProjectSelection.Project(2, 0, 1);
        ProjectSelection.Project project3 = new ProjectSelection.Project(3, 0, 2);
        ProjectSelection.Project project4 = new ProjectSelection.Project(4, 0, 10);
        project1.addRequirement(2);
        project1.addRequirement(3);
        List<ProjectSelection.Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        // Feasible project set: 1, 2, 3
        Assertions.assertEquals(2, ProjectSelection.maximumProjects(projects));
    }

    @Test
    public void example3() {
        ProjectSelection.Project project1 = new ProjectSelection.Project(1, 5, 0);
        ProjectSelection.Project project2 = new ProjectSelection.Project(2, 0, 1);
        ProjectSelection.Project project3 = new ProjectSelection.Project(3, 0, 2);
        ProjectSelection.Project project4 = new ProjectSelection.Project(4, 0, 1);
        project1.addRequirement(2);
        project1.addRequirement(3);
        project1.addRequirement(4);
        List<ProjectSelection.Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        // Feasible project set: 1, 2, 3, 4
        Assertions.assertEquals(1, ProjectSelection.maximumProjects(projects));
    }

    @Test
    public void exampleNoSolution() {
        ProjectSelection.Project project1 = new ProjectSelection.Project(1, 5, 0);
        ProjectSelection.Project project2 = new ProjectSelection.Project(2, 0, 1);
        ProjectSelection.Project project3 = new ProjectSelection.Project(3, 0, 20);
        project1.addRequirement(2);
        project1.addRequirement(3);
        List<ProjectSelection.Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        Assertions.assertEquals(0, ProjectSelection.maximumProjects(projects));
    }

    @Test()
    public void example4() {
        ProjectSelection.Project project1 = new ProjectSelection.Project(1, 5, 0);
        ProjectSelection.Project project2 = new ProjectSelection.Project(2, 0, 1);
        ProjectSelection.Project project3 = new ProjectSelection.Project(3, 0, 2);
        ProjectSelection.Project project4 = new ProjectSelection.Project(4, 3, 0);
        ProjectSelection.Project project5 = new ProjectSelection.Project(5, 0, 1);
        ProjectSelection.Project project6 = new ProjectSelection.Project(6, 0, 10);
        project1.addRequirement(2);
        project1.addRequirement(3);
        project4.addRequirement(5);
        project4.addRequirement(6);
        List<ProjectSelection.Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);
        projects.add(project6);
        // Feasible project set: 1, 2, 3
        Assertions.assertEquals(2, ProjectSelection.maximumProjects(projects));
    }

    @Test()
    public void testSamePrerequisite() {
        ProjectSelection.Project project1 = new ProjectSelection.Project(1, 1, 0);
        ProjectSelection.Project project2 = new ProjectSelection.Project(2, 3, 0);
        ProjectSelection.Project project3 = new ProjectSelection.Project(3, 0, 1);
        ProjectSelection.Project project4 = new ProjectSelection.Project(4, 0, 2);
        project1.addRequirement(3);
        project1.addRequirement(4);
        project2.addRequirement(3);
        project2.addRequirement(4);
        List<ProjectSelection.Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        // Feasible project set: 1, 2, 3, 4
        Assertions.assertEquals(1, ProjectSelection.maximumProjects(projects));
    }

}
