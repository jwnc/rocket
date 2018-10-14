package bobo.visual.maze.linego;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class AlgoFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight) {

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setVisible(true);
    }

    public AlgoFrame(String title) {

        this(title, 1024, 768);
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    // data
    private MazeData data;

    public void render(MazeData data) {
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel {

        public AlgoCanvas() {
            // 双缓存
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            // 抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            // 具体绘制
            final int w = AlgoVisualizer.blockSide;
            final int h = AlgoVisualizer.blockSide;

            for (int i = 0; i < data.N(); i++)
                for (int j = 0; j < data.M(); j++) {

                    if (data.maze[i][j] != MazeData.ROAD) {
                        int ww = 0;
                        int hh = 0;
                        if (i % 2 == 0) {
                            hh = AlgoVisualizer.lineHeight;
                            ww = j % 2 == 0 ? AlgoVisualizer.lineHeight : w;
                        } else {
                            hh = h;
                            ww = AlgoVisualizer.lineHeight;
                        }

                        if (data.maze[i][j] == MazeData.BLANK) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);//变成路的墙
                        } else if (data.maze[i][j] == MazeData.WALL) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);//墙
                        }

                        if (data.inMist[i][j]) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);//迷雾效果
                        }
                        if (data.path[i][j]) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Yellow);//走路效果
                        }

                        AlgoVisHelper.fillRectangle(g2d, j / 2 * w + (j - j / 2) * AlgoVisualizer.lineHeight, i / 2 * h + (i - i / 2) * AlgoVisualizer.lineHeight, ww, hh);
                    } else {
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                        if (data.inMist[i][j]) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);
                        }
                        if (data.path[i][j]) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Yellow);
                        }
                        AlgoVisHelper.fillRectangle(g2d, j / 2 * w + (j - j / 2) * AlgoVisualizer.lineHeight, i / 2 * h + (i - i / 2) * AlgoVisualizer.lineHeight, w, h);
                    }
                }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}

