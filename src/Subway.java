import java.util.Arrays;
import java.util.List;

public class Subway {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("请输入命令行参数!");
        } else {
            List<String> argsList = Arrays.asList(args);
            if (argsList.contains("-map")) {
                int mapIndex = argsList.indexOf("-map");
                if (mapIndex + 1 >= argsList.size() || argsList.get(mapIndex + 1).startsWith("-")) {
                    System.err.println("请在-map参数后输入你需要获取的自定义地铁的文件路径!");
                } else {
                    String destFilePath = argsList.get(mapIndex + 1);
                    Output.map(destFilePath);
                }
            } else if (argsList.contains("-a")) {
                int aIndex = argsList.indexOf("-a");
                if (aIndex + 1 >= argsList.size() || argsList.get(aIndex + 1).startsWith("-")) {
                    System.err.println("请在-a参数后输入你要查询的地铁线路名!");
                } else if (!argsList.contains("-o")) {
                    System.err.println("在输入了-a参数之后, 你需要输入-o参数!");
                } else {
                    int oIndex = argsList.indexOf("-o");
                    if (oIndex + 1 >= argsList.size() || argsList.get(oIndex + 1).startsWith("-")) {
                        System.err.println("请在-o参数后输入你查询地铁线路之后需要输出的文件路径!");
                    } else {
                        String lineName = argsList.get(aIndex + 1);
                        String destFilePath = argsList.get(oIndex + 1);
                        Output.line(lineName, destFilePath);
                    }
                }
            } else if (argsList.contains("-b")) {
                int bIndex = argsList.indexOf("-b");
                if (bIndex + 1 >= argsList.size() || argsList.get(bIndex + 1).startsWith("-")) {
                    System.err.println("请在-b参数后输入出发站点!");
                } else if (bIndex + 2 >= argsList.size() || argsList.get(bIndex + 2).startsWith("-")) {
                    System.err.println("请在-b参数后输入目的站点!");
                } else if (!argsList.contains("-o")) {
                    System.err.println("在输入了-b参数之后, 你需要输入-o参数!");
                } else {
                    int oIndex = argsList.indexOf("-o");
                    if (oIndex + 1 >= argsList.size() || argsList.get(oIndex + 1).startsWith("-")) {
                        System.err.println("请在-o参数后输入你查询地铁线路之后需要输出的文件路径!");
                    } else {
                        String startStation = argsList.get(bIndex + 1);
                        String endStation = argsList.get(bIndex + 2);
                        String destFilePath = argsList.get(oIndex + 1);
                        Output.distance(startStation, endStation, destFilePath);
                    }
                }
            } else {
                System.err.println("请正确输入命令行参数!");
            }
        }
    }
}
