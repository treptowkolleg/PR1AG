package pr1.a09;

public interface ObjectFactory<T> {

    boolean validate(String[] parts);

    T map(String[] parts);
}
