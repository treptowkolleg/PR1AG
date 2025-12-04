package pr1.tests.people;


import pr1.tests.people.entity.Person;

public class People {

    public static void main(String[] args) {
        PopulationList people = PopulationList.of(new Person("Benjamin", "Wagner", 38));

        people.forEach(System.out::println);
    }

}
