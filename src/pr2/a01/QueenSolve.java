package pr2.a01;

import treptowkolleg.edu.text.IOApplication;
import treptowkolleg.edu.tools.StopWatch;

public class QueenSolve extends IOApplication {

    public static void main(String[] args) {
        new QueenSolve();
    }

    @Override
    public void run() throws Exception {
        GameBoard gameBoard = new GameBoard(8);
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        if (gameBoard.isSolved()) {
            stopWatch.stop();
            gameBoard.printSolution(getConsolePrintWriter());
            printf("Lösung gefunden in %d Schritten und %.2f Sekunden%n", gameBoard.getCount(), stopWatch.getElapsedSeconds());
            println("Damen:");
            for (Queen queen : gameBoard.getSolution()) {
                printf("%s%n", gameBoard.getQueenPositionFrom(queen));
            }
        } else {
            stopWatch.stop();
            println("Keine gültige Aufstellung gefunden.");
        }
    }
}
