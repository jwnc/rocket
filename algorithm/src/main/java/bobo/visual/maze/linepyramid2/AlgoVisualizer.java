package bobo.visual.maze.linepyramid2;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * 将墙改成线
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/10/12 9:44
 */
public class AlgoVisualizer {

    private static int DELAY = 5;
    public static int blockSide = 8;
    public static final int BLOCK_FIXED = 800; // 除以行数得到blockSide
    public static final int lineHeight = 2;

    final int LEFT = 3;
    final int TOP = 0;

    private MazeData dataLeft;
    private MazeData dataRight;
    private MazeData dataMerge; // 两个入口合并后的效果

    private AlgoFrame frame;
    public static final int d[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private boolean viewResolve = false;

    public AlgoVisualizer(int N, int M) {

        // 初始化数据
        dataLeft = new MazeData(N, M).setEntrance(N - 2, 0).setEntrancePosition(N - 2, 1);
        dataRight = new MazeData(N, M).setEntrance(N - 2, M - 1).setEntrancePosition(N - 2, M - 2);
        blockSide = BLOCK_FIXED / N;
        int sceneHeight = N / 2 * blockSide + (N - N / 2) * lineHeight + AlgoFrame.fixedOffset;
        int sceneWidth = M / 2 * blockSide + (M - M / 2) * lineHeight + AlgoFrame.fixedOffset;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generation Visualization", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run() {
        run1(dataLeft);
        run1(dataRight);
        go(dataLeft, false);
        System.out.println(Arrays.toString(dataLeft.path[10]));
        go(dataRight, false);
        merge(dataLeft, dataRight);
        setData(dataMerge, -1, -1);
    }

    private void merge(MazeData dataLeft, MazeData dataRight) {
        dataMerge = dataLeft.clone();
        dataMerge.setEntrance(dataRight.getEntranceX(), dataRight.getEntranceY());
        // 同步dataRight的路径数据
        for (int i = 0; i < dataRight.N(); i++) {
            for (int j = 0; j < dataRight.M(); j++) {
                if (dataRight.path[i][j]) {
                    System.out.println("path...");
//                    dataMerge.path[i][j] = true;
                    dataMerge.maze[i][j] = dataRight.maze[i][j];
                }
            }
        }
    }

    private void run1(MazeData mazeData) {
        setData(mazeData, -1, -1);

        RandomQueue<Position> queue = new RandomQueue<Position>();
        Position first = mazeData.getFirstPosition();//首个入栈路线, 别和入口坐标搞混了
        queue.add(first);
        mazeData.visited[first.getX()][first.getY()] = true;
        mazeData.openMist(first.getX(), first.getY());

        while (queue.size() != 0) {
            Position curPos = queue.remove();

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;

                if (mazeData.inArea(newX, newY)
                        && !mazeData.visited[newX][newY]
                        && mazeData.maze[newX][newY] == MazeData.ROAD) {
                    queue.add(new Position(newX, newY));
                    mazeData.visited[newX][newY] = true;
                    mazeData.openMist(newX, newY);
                    setData(mazeData, curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }

        setData(mazeData, -1, -1);
    }

    private void setData(MazeData mazeData, int x, int y) {
        if (mazeData.inArea(x, y))
            mazeData.maze[x][y] = MazeData.BLANK; //TODO 改为空白线

        frame.render(mazeData);
        AlgoVisHelper.pause(DELAY);
    }

    /***
     * 模拟求解
     * @since 2018/10/14 22:58
     * @param mazeData
     * @param b 控制是否可视化过程
     * @return void
     */
    private void go(MazeData mazeData, boolean b) {
        viewResolve = b;

        for (int i = 0; i < mazeData.N(); i++)
            for (int j = 0; j < mazeData.M(); j++)
                mazeData.visited[i][j] = false;

        go(mazeData, mazeData.getEntranceX(), mazeData.getEntranceY());
    }

    private boolean go(MazeData mazeData, int x, int y) {
        if (!mazeData.inArea(x, y))
            throw new IllegalArgumentException("x,y are out of index in go function!");

        mazeData.visited[x][y] = true;
        setPathData(mazeData, x, y, true);

        if (x == mazeData.getExitX() && y == mazeData.getExitY())
            return true;

        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if (mazeData.inArea(newX, newY) &&
                    mazeData.maze[newX][newY] != MazeData.WALL &&
                    !mazeData.visited[newX][newY])
                if (go(mazeData, newX, newY))
                    return true;
        }

        // 回溯
        setPathData(mazeData, x, y, false);

        return false;
    }

    private void setPathData(MazeData mazeData, int x, int y, boolean isPath) {
        if (mazeData.inArea(x, y))
            mazeData.path[x][y] = isPath;
        if (viewResolve) {
            frame.render(mazeData);
            AlgoVisHelper.pause(DELAY);
        }
    }

    private class AlgoKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent event) {
            if (event.getKeyChar() == '1') {
                DELAY = 20;
                new Thread(() -> {
                    dataMerge.setEntrance(dataLeft.getEntranceX(), dataLeft.getEntranceY());
                    go(dataMerge, true);
                }).start();
            }

            if (event.getKeyChar() == '2') {
                DELAY = 20;
                new Thread(() -> {
                    dataMerge.setEntrance(dataRight.getEntranceX(), dataRight.getEntranceY());
                    go(dataMerge, true);
                }).start();
            }
        }
    }

    public static void main(String[] args) {

        int N = 43; // 2n+3
        int M = 83; //4n+3

        AlgoVisualizer vis = new AlgoVisualizer(N, M);

    }
}