package bobo.visual.maze.linepyramid;

/**
 * 模拟金字塔
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/10/14 0:27
 */
public class MazeData {
    public static final char ENTRANCE = '^';
    public static final char EXIT = '$';
    public static final char ROAD = ' ';
    public static final char WALL = '#';
    public static final char BLANK = ':'; //空白线,由墙打通变成
    public static final char UNKNOWN = '*'; //墙外世界,不用汇出

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
        if (M % 4 != 3)
            throw new IllegalArgumentException("必须满足M%4 == 3"); //因为以中心点开始, 每行格子数为2n+1,那么格子两边的线为2n+2
        if (M - 2 * N != -3)
            throw new IllegalArgumentException("必须满足N- 2* M == -3"); // 首行格子数为1, 末行格子数为2n+1, 格子行数为 n+1,边框行数为n+2,总行数N=2n+3

        this.N = N;
        this.M = M;

        maze = new char[N][M];
        visited = new boolean[N][M];
        inMist = new boolean[N][M];
        path = new boolean[N][M];
        for (int i = 1; i < N; i += 2)// 根据空白格判断, 反推边线的情况
            for (int j = 1; j < M; j += 2) {
                if (isCell(i, j)) {
                    maze[i][j] = ROAD;
                    maze[i][j + 1] = WALL;
                    maze[i][j - 1] = WALL;
                    maze[i + 1][j] = WALL;
                    maze[i + 1][j + 1] = WALL;
                    maze[i + 1][j - 1] = WALL;
                    maze[i - 1][j] = WALL;
                    maze[i - 1][j + 1] = WALL;
                    maze[i - 1][j - 1] = WALL;
                } else {
                    maze[i][j] = UNKNOWN;
                    if (j < M / 2) { // 左边单元格
                        maze[i][j - 1] = UNKNOWN;
                        maze[i - 1][j] = UNKNOWN;
                        maze[i - 1][j - 1] = UNKNOWN;
                    } else {// 右边单元格
                        maze[i][j + 1] = UNKNOWN;
                        maze[i - 1][j] = UNKNOWN;
                        maze[i - 1][j + 1] = UNKNOWN;
                    }
                }


                visited[i][j] = false;
                inMist[i][j] = false;
                path[i][j] = false;
            }

        exitX = 0;
        exitY = M / 2;
        entranceX = N - 2;
        entranceY = M - 1;

        maze[entranceX][entranceY] = ENTRANCE;
        maze[exitX][exitY] = EXIT;
    }

    /***
     * 判断是否可视格子
     * @since 2018/10/14 0:29
     * @param i
     * @param j
     * @return boolean
     */
    private boolean isCell(int i, int j) {
        int cellAll = M / 2; // 一行所有的格子数
        int cellsInRow = i;//该行可视格子数
        int firstVisualCell = (cellAll - cellsInRow) / 2 + 1; // 格子序号从1开始
        int lastVisualCell = cellAll - (cellAll - cellsInRow) / 2;
        int cellOfJ = j / 2 + 1;
        return cellOfJ >= firstVisualCell && cellOfJ <= lastVisualCell; //或者Math.abs(cellAll - j) <  cellsInRow;
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
