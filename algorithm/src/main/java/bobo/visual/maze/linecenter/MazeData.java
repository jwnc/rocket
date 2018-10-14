package bobo.visual.maze.linecenter;

public class MazeData {
    public static final char ENTRANCE = '^';
    public static final char EXIT = '$';
    public static final char ROAD = ' ';
    public static final char WALL = '#';
    public static final char BLANK = ':'; //空白线

    private int N, M;
    public char[][] maze;
    public boolean[][] visited;
    public boolean[][] inMist;
    public boolean[][] path;

    private int entranceX, entranceY;
    private int exitX, exitY;

    public MazeData(int N, int M) {

        if (N % 2 == 0 || M % 2 == 0)
            throw new IllegalArgumentException("Our Maze Generalization Algorihtm requires the width and height of the maze are odd numbers");
        if (N % 4 != 3)
            throw new IllegalArgumentException("必须满足N%4 == 3"); //因为以中心点开始, 每行格子数为2n+1,那么格子两边的线为2n+2

        this.N = N;
        this.M = M;

        maze = new char[N][M];
        visited = new boolean[N][M];
        inMist = new boolean[N][M];
        path = new boolean[N][M];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                if (i % 2 == 1 && j % 2 == 1)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;

                visited[i][j] = false;
                inMist[i][j] = false;
                path[i][j] = false;
            }

        entranceX = N / 2;
        entranceY = M / 2;
        exitX = N - 2;
        exitY = M - 1;

        maze[entranceX][entranceY] = ENTRANCE;
        maze[exitX][exitY] = EXIT;
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public int getEntranceX() {
        return entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void openMist(int x, int y) {
        if (!inArea(x, y))
            throw new IllegalArgumentException("x or y is out of index in openMist function!");

        for (int i = x - 1; i <= x + 1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                if (inArea(i, j))
                    inMist[i][j] = false;

        return;
    }
}
