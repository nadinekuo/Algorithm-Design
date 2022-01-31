import org.junit.Test;
import static org.junit.Assert.*;

public class CountSkillsCompletedTest {

    @Test
    public void ThreeSkillsTwoCompleted() {
        int solution = 2;
        int n = 3;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[4];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", false);
        skills[2] = new CountSkillsCompleted.Skill("Huffman encoding", true);
        skills[3] = new CountSkillsCompleted.Skill("Exchange arguments", true);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }

    @Test
    public void ThreeSkillsOneCompleted() {
        int solution = 1;
        int n = 3;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[4];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", false);
        skills[2] = new CountSkillsCompleted.Skill("Huffman encoding", false);
        skills[3] = new CountSkillsCompleted.Skill("Exchange arguments", true);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }

    @Test
    public void NothingCompleted() {
        int solution = 0;
        int n = 3;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[4];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", false);
        skills[2] = new CountSkillsCompleted.Skill("Huffman encoding", false);
        skills[3] = new CountSkillsCompleted.Skill("Exchange arguments", false);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }

    @Test
    public void AllCompleted() {
        int solution = 3;
        int n = 3;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[4];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", true);
        skills[2] = new CountSkillsCompleted.Skill("Huffman encoding", true);
        skills[3] = new CountSkillsCompleted.Skill("Exchange arguments", true);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }

    @Test
    public void OneSkillCompleted() {
        int solution = 1;
        int n = 1;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[2];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", true);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }

    @Test
    public void OneIncomplete() {
        int solution = 0;
        int n = 1;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[2];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", false);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }


    @Test
    public void FourSkillsOneComplete() {
        int solution = 1;
        int n = 4;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[5];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", false);
        skills[2] = new CountSkillsCompleted.Skill("Huffman encoding", false);
        skills[3] = new CountSkillsCompleted.Skill("Exchange arguments", false);
        skills[4] = new CountSkillsCompleted.Skill("Dijkstra", true);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }

    @Test
    public void FourSkillsTwoComplete() {
        int solution = 2;
        int n = 4;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[5];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", false);
        skills[2] = new CountSkillsCompleted.Skill("Huffman encoding", false);
        skills[3] = new CountSkillsCompleted.Skill("Exchange arguments", true);
        skills[4] = new CountSkillsCompleted.Skill("Dijkstra", true);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }

    @Test
    public void FourSkillsThreeComplete() {
        int solution = 3;
        int n = 4;
        CountSkillsCompleted.Skill[] skills = new CountSkillsCompleted.Skill[5];
        skills[1] = new CountSkillsCompleted.Skill("Binary counting", false);
        skills[2] = new CountSkillsCompleted.Skill("Huffman encoding", true);
        skills[3] = new CountSkillsCompleted.Skill("Exchange arguments", true);
        skills[4] = new CountSkillsCompleted.Skill("Dijkstra", true);
        assertEquals(solution, CountSkillsCompleted.numberOfCompletedSkills(n, skills));
    }


}
