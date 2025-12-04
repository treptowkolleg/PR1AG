package pr1.tests.maze;

public class Maze {

	public static void main(String[] args) throws InterruptedException {
		int n = 10;
		MazeFieldState[][] maze = new MazeFieldState[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				maze[i][j] = MazeFieldState.FIELD;
			}
		}
		maze[8][0] = MazeFieldState.WALL;
		maze[8][1] = MazeFieldState.WALL;
		maze[8][3] = MazeFieldState.WALL;
		maze[8][4] = MazeFieldState.WALL;
		maze[4][2] = MazeFieldState.WALL;
		maze[5][2] = MazeFieldState.WALL;
		maze[6][2] = MazeFieldState.WALL;
		maze[7][2] = MazeFieldState.WALL;
		maze[8][2] = MazeFieldState.WALL;
		maze[8][8] = MazeFieldState.TARGET;

		System.out.println("Weg vom Ziel zurückverfolgen!");
		boolean exitFound = canExit(maze, 3, 0, n);
		if (exitFound) {
			System.out.println("Weg gefunden!");
			for (int i = 0; i < n; i++) {
				StringBuilder line = getLine(n, maze, i);
				System.out.println(line);
			}
		}
		System.out.println("Spiel beendet!");
	}

	private static StringBuilder getLine(int n, MazeFieldState[][] maze, int i) {
		StringBuilder line = new StringBuilder();
		for (int j = 0; j < n; j++) {
			switch (maze[i][j]) {
			case WALL:
				line.append("# ");
				break;
			case VISITED:
				line.append("o ");
				break;
			case TARGET:
				line.append("X ");
				break;
			default:
				line.append(". ");
			}
		}
		return line;
	}

	public static boolean canExit(MazeFieldState[][] maze, int x, int y, int n) throws InterruptedException {

		if (x < 0 || y < 0 || x > n - 1 || y > n - 1
				|| (maze[x][y] != MazeFieldState.FIELD && maze[x][y] != MazeFieldState.TARGET)) {
			return false;
		}
		if (maze[x][y] == MazeFieldState.TARGET) {
			return true;
		}
		maze[x][y] = MazeFieldState.VISITED;
		if (canExit(maze, x + 1, y, n) || canExit(maze, x, y + 1, n) || canExit(maze, x - 1, y, n)
				|| canExit(maze, x, y - 1, n)) {
			Thread.sleep(250);
			System.out.printf("-> (%d,%d)%n", x, y);
			return true;
		}
		return false;
	}

}
