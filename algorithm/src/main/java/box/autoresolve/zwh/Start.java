package box.autoresolve.zwh;

import box.autoresolve.zwh.map.MapChecker;
import box.autoresolve.zwh.map.SokobanMap;
import box.autoresolve.zwh.solver.ISokobanSolver;
import box.autoresolve.zwh.solver.ViolenceConcurrentSolver;
import box.autoresolve.zwh.solver.ViolentSingleSolver;
import box.autoresolve.zwh.solver.judge.DefaultJudger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 需要手动输入迷宫, 示例如下, 迷宫地图可以到resources\zwh下面的levels.rar里面找
 * <p>
 * ####____
 * #--#____
 * #--#____
 * #--#####
 * #.$$-$@#
 * #--.-.-#
 * #--#####
 * ####____
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/10/17 15:00
 */
public class Start {

    public static void main(String[] args) {
        run();
        //	runWithAverageTime();
    }

    private static void run() {
        SokobanMap map = readMap();
        ISokobanSolver solver = new ViolenceConcurrentSolver(new DefaultJudger());
        // ISokobanSolver solver = new ViolentSingleSolver(new DefaultJudger());
        if (MapChecker.isValidMap(map)) {
            try {
                solver.solve(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SokobanMap resMap = solver.getSolvedMap();
            System.out.println("Time: " + solver.getSolvedTime());
            if (resMap != null) {
                System.out.println(resMap.path);
            } else {
                System.out.println("can not find the way to solve the SokobanMap");
            }
        } else {
            System.out.println("this is not valid sokoban map");
        }
    }

    private static void runWithAverageTime() {
        long time = 0;
        final int count = 100;
        int successCount = 0;
        SokobanMap map = readMap();
        for (int i = 0; i < count; i++) {
            // ISokobanSolver solver = new ViolenceConcurrentSolver(new
            // DefaultJudger());
            ISokobanSolver solver = new ViolentSingleSolver(new DefaultJudger());
            if (MapChecker.isValidMap(map)) {
                try {
                    solver.solve(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SokobanMap resMap = solver.getSolvedMap();
                System.out.println("Time: " + solver.getSolvedTime());
                if (resMap != null) {
                    time += solver.getSolvedTime();
                    System.out.println(resMap.path);
                    successCount++;
                } else {
                    System.out.println("can not find the way to solve the SokobanMap");
                }
            } else {
                System.out.println("this is not valid sokoban map");
            }
        }
        System.out.println("Average: " + time / successCount);
    }

    private static SokobanMap readMap() {
        System.err.println("请在下方输入迷宫(可到http://sokoban.cn/sokoplayer/SokoPlayer_HTML5.php或者resources\\zwh下面的levels.rar找标准格式的迷宫):");
        Scanner scanner = new Scanner(System.in);
        List<String> mapList = new ArrayList<String>();
        while (true) {
            String temp = scanner.nextLine();
            if (temp.equals("end")) {
                break;
            }
            mapList.add(temp);
        }
        return new SokobanMap(mapList, "");
    }
}
