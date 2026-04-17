package pr2.a01;

public class Field {
    private final int x;
    private final int y;
    private FieldState fieldState;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.fieldState = FieldState.EMPTY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }
}
