import java.util.Arrays;
import java.util.Objects;

public class PersonSort {



    public static class Person implements Comparable<Person> {

        private int age;
        private long id;

        public Person(int age, long id) {
            this.age = age;
            this.id = id;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", id=" + id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && id == person.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(age, id);
        }

        @Override
        public int compareTo(Person other) {
            return Integer.compare(this.age, other.age);
        }
    }




    public static void main(String[] args) {

        //Random rng = new Random();
        Person[] people = new Person[1000];

        for(int i = 0; i < 1000; i++){
            long id = (long) Math.floor(Math.random() * 1000);
            int age = (int) Math.floor(Math.random() * 100);

            people[i] = new Person(age, id);

            //people[i] = new Person(rng.nextInt(), rng.nextInt());
        }

        sortPeople(people);

        for (int p = 0; p < people.length; p++) {
            System.out.println(people[p]);
        }
    }




    public static void sortPeople(Person[] peopleToSort) {

        // SORT 1: array.sort

        Arrays.sort(peopleToSort);
    }



}
