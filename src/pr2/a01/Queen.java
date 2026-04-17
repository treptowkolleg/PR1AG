package pr2.a01;

public record Queen(Field field) {

    public int getX() {
        return field.getX();
    }

    public int getY() {
        return field.getY();
    }
}
