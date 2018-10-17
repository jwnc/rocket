package box.autoresolve.qihe.gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;

public class ImagePanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        //super.paintComponents(g);
        super.paint(g);
        int x = 0, y = 0;
        ImageIcon icon = new ImageIcon("algorithm/src/main/resources/qihe/背景.jpg");
        g.drawImage(icon.getImage(), x, y, getSize().width,//图片和pannel一样大
                getSize().height, this);// 图片会自动缩放
    }

   /* @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        int x = 0, y = 0;
        ImageIcon icon = new ImageIcon("algorithm/src/main/resources/qihe/背景.jpg");
        g.drawImage(icon.getImage(), x, y, getSize().width,//图片和pannel一样大
                getSize().height, this);// 图片会自动缩放
//    g.drawImage(icon.getImage(), x, y,this);//图片不会自动缩放
    }*/

}
