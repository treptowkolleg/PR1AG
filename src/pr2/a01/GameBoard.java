package pr2.a01;

import java.io.PrintWriter;
import java.util.*;

public class GameBoard {
    private static final int SIZE = 8;
    private final int QUEENS;
    private final List<Field> fields = new ArrayList<>();
    private final List<Queen> solution = new ArrayList<>();
    private final Map<Integer, Queen> occupiedRows = new HashMap<>();
    private final Map<Integer, Queen> occupiedCols = new HashMap<>();
    private final Map<Integer, Queen> occupiedDiagMain = new HashMap<>();
    private final Map<Integer, Queen> occupiedDiagAnti = new HashMap<>();
    private int count = 0;

    private GameState state = GameState.SEARCHING;

    public GameBoard(int queenAmount) {
        QUEENS = queenAmount;
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                fields.add(new Field(x, y));
            }
        }
        int i = 0;
    }

    public String getQueenPositionFrom(Queen queen) {
        char col = (char) ('A' + queen.getX());
        int row = SIZE - queen.getY();

        return String.format("%c%d", col, row);
    }

    public int getCount() {
        return count;
    }

    /**
     * Prüft, ob ein Feld frei von Bedrohungen ist.
     * Nutzt die HashMaps, anstatt alle Felder zu scannen
     */
    private boolean isSafe(int x, int y) {
        return !occupiedRows.containsKey(y) &&
                !occupiedCols.containsKey(x) &&
                !occupiedDiagMain.containsKey(x - y) &&
                !occupiedDiagAnti.containsKey(x + y);
    }

    /**
     * Platziert eine Dame und markiert die betroffenen Linien in den HashMaps.
     */
    private void placeQueen(Field field) {
        Queen queen = new Queen(field);

        field.setFieldState(FieldState.QUEEN);
        solution.add(queen);
        occupiedRows.put(field.getY(), queen);
        occupiedCols.put(field.getX(), queen);
        occupiedDiagMain.put(field.getX() - field.getY(), queen);
        occupiedDiagAnti.put(field.getX() + field.getY(), queen);
    }

    /**
     * Nimmt die letzte Dame zurück (Backtracking-Schritt) und räumt die HashMaps auf.
     */
    private void removeLastQueen() {
        if (solution.isEmpty()) {
            return;
        }
        Queen queen = solution.remove(solution.size() - 1);

        Field field = queen.field();
        field.setFieldState(FieldState.EMPTY);
        occupiedRows.remove(field.getY());
        occupiedCols.remove(field.getX());
        occupiedDiagMain.remove(field.getX() - field.getY());
        occupiedDiagAnti.remove(field.getX() + field.getY());
    }

    public boolean isSolved() {
        return solved(0);
    }

    /**
     * Backtracking-Algorithmus: Versucht rekursiv, Damen zeilenweise zu platzieren.
     *
     * @param currentRow Die Zeile, in der aktuell eine Dame platziert werden soll
     * @return true, wenn Lösung gefunden, false, wenn Sackgasse
     */
    public boolean solved(int currentRow) {
        if (solution.size() == QUEENS) {
            state = GameState.SOLVED;
            return true;
        }
        for (int i = currentRow; i < fields.size(); i++) {
            Field field = fields.get(i);
            int x = field.getX();
            int y = field.getY();

            if (field.getFieldState() == FieldState.EMPTY && isSafe(x, y)) {
                count++;
                placeQueen(field);
                if (solved(i + 1)) {
                    return true;
                }
                removeLastQueen();
                count++;
            }
        }
        return false;
    }

    public void printSolution(PrintWriter out) {
        if (state != GameState.SOLVED) {
            out.println("Keine Lösung gefunden.");
            return;
        }
        out.println("Gefundene Lösung:");
        for (int y = 0; y < SIZE; y++) {
            StringBuilder row = new StringBuilder();

            for (int x = 0; x < SIZE; x++) {
                Field f = fields.get(y * SIZE + x);
                row.append(f.getFieldState() == FieldState.QUEEN ? "Q " : ". ");
            }
            out.println(row);
        }
    }

    public List<Queen> getSolution() {
        return Collections.unmodifiableList(solution);
    }

    public GameState getState() {
        return state;
    }
}
