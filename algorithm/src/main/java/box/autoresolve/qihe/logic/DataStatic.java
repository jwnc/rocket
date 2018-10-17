package box.autoresolve.qihe.logic;

import box.autoresolve.qihe.gui.PeopleLabel;
import box.autoresolve.qihe.struct.MyPoint;
import box.autoresolve.qihe.struct.SimpleSitu;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DataStatic {
    public static int sleepTime = 10;
    public static int picSize = 35, picNum = 20, changdu = picSize * picNum;
    public static int zuo, shang;
    public static JLayeredPane fenceng;
    public static PeopleLabel peopleLabel;
    public static Map<MyPoint, JLabel> boxLabelMap = new HashMap<>(10);

    public static List<MyPoint> boxList = new ArrayList<>(10);
    public static List<MyPoint> zhongdian = new ArrayList<>(10);
    public static int peopleValue;
    public static char[][] map;
    public static int chang, kuan;
    public static int boxNum;
    public static HashSet<MyPoint> failPoint = new HashSet<>(30);
    public static HashSet<SimpleSitu> allSitu = new HashSet<>(100);

    public static int pointToValue(MyPoint myPoint) {
        return myPoint.y * DataStatic.chang + myPoint.x;
    }

    public static int pointToValue(int y, int x) {
        return y * DataStatic.chang + x;
    }

    public static MyPoint valueToPoint(int value) {
        return new MyPoint(value % DataStatic.chang, value / DataStatic.chang);
    }
}
