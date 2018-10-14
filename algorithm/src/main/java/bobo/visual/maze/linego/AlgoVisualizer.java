package bobo.visual.maze.linego;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 将墙改成线
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/10/12 9:44
 */
public class AlgoVisualizer {

    private static int DELAY = 5;
    public static final int blockSide = 8;
    public static final int lineHeight = 2;
    final int LEFT = 3;
    final int TOP = 0;

    private MazeData data;
    private AlgoFrame frame;
    public static final int d[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public AlgoVisualizer(int N, int M) {

        // 初始化数据
        data = new MazeData(N, M);
        int sceneHeight = data.N() * blockSide - (data.N() / 2 + 1) * (blockSide - lineHeight);
        int sceneWidth = data.M() * blockSide - (data.N() / 2 + 1) * (blockSide - lineHeight);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generation Visualization", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run1() {

        setData(-1, -1);
    }

    private void run() {

        setData(-1, -1);

        RandomQueue<Position> queue = new RandomQueue<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(), first.getY());

        while (queue.size() != 0) {
            Position curPos = queue.remove();

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;

                if (data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.maze[newX][newY] == MazeData.ROAD) {
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.openMist(newX, newY);
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }

        setData(-1, -1);
    }

    private void setData(int x, int y) {
        if (data.inArea(x, y))
            data.maze[x][y] = MazeData.BLANK; //TODO 改为空白线


        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private boolean go(int x, int y) {

        if (!data.inArea(x, y))
            throw new IllegalArgumentException("x,y are out of index in go function!");

        data.visited[x][y] = true;
        setPathData(x, y, true);

        if (x == data.getExitX() && y == data.getExitY())
            return true;

        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if (data.inArea(newX, newY) &&
                    data.maze[newX][newY] != MazeData.WALL &&
                    !data.visited[newX][newY] && !isDeadWay(newX, newY, i))
                if (go(newX, newY))
                    return true;
        }

        // 回溯
        setPathData(x, y, false);

        return false;
    }

    // 处理各个边线上的情况, 减少分支
    private boolean isDeadWay(int newX, int newY, int i) {
        if ((newX == 1 || newX == data.N() - 2) && (i == LEFT)) {// 上下边线的时候, 不能左拐
            return true;
        }

        if ((newY == 1 || newY == data.M() - 2) && (i == TOP)) {// 左右边线的时候, 不能上拐
            return true;
        }

        return false;
    }

    private void setPathData(int x, int y, boolean isPath) {
        if (data.inArea(x, y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent event) {
            if (event.getKeyChar() == ' ') {
                DELAY = 50;
                for (int i = 0; i < data.N(); i++)
                    for (int j = 0; j < data.M(); j++)
                        data.visited[i][j] = false;

                new Thread(() -> {
                    go(data.getEntranceX(), data.getEntranceY());
                }).start();
            }
        }
    }

    public static void main(String[] args) {

        int N = 55;
        int M = 55;

        AlgoVisualizer vis = new AlgoVisualizer(N, M);

    }
}