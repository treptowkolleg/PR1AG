package pr1.tests.people;

import pr1.tests.people.entity.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PopulationList extends ArrayList<Person> {

    public PopulationList() {
    }

    public PopulationList(int initialCapacity) {
        super(initialCapacity);
    }

    public PopulationList(Collection<? extends Person> collection) {
        super(collection);
    }

    public PopulationList(HashSet<? extends Person> hashSet) {
        super(hashSet);
    }

    public static  PopulationList of(Person ...people) {
        return new PopulationList(List.of(people));
    }

}
