import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Output {

    private static final String sourceFilePath = "SubwayMessage.txt";

    /**
     * 获得对应的自定义地铁文件
     *
     * @param destFilePath
     */
    public static void map(String destFilePath) {
        Path sourcePath = Paths.get(sourceFilePath);
        Path destPath = Paths.get(destFilePath);
        try {
            // 第三个参数表示覆盖已经存在的文件
            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得指定地铁线的站点
     *
     * @param lineName
     * @param destFilePath
     */
    public static void line(String lineName, String destFilePath) {
        List<String> lineData = getLineData();

        // 写文件
        BufferedWriter writer = null;
        boolean flag = false;
        for (String line : lineData) {
            String[] split = line.split(":");
            String lineNameStr = split[0];
            if (lineNameStr.equals(lineName)) {
                String stationStr = split[1];
                try {
                    writer = new BufferedWriter(new FileWriter(destFilePath));
                    writer.write(stationStr + "\r\n");
                    writer.flush();

                    flag = true; // 表示有这个地铁线
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if(!flag) {
            System.err.println("你输入的地铁线不存在!");
        }
    }

    /**
     * 获取两站之间距离最短的路线
     *
     * @param startStation
     * @param endStation
     * @param destFilePath
     */
    public static void distance(String startStation, String endStation, String destFilePath) {
        List<String> lineData = getLineData();

        // 加载到内存, 去计算
        Map<String, List<Station>> subwayMessage = SubwayMessage.getSubwayMessage(lineData);

        // 计算最短路径
        Distance distance = new Distance();
        Station s1 = new Station(startStation);
        Station s2 = new Station(endStation);
        distance.calculate(s1, s2);

        // 输出到文件
        List<Station> arrayList = new ArrayList<>();
        LinkedHashSet<Station> allPassedStations = s1.getAllPassedStations(s2);
        arrayList.addAll(allPassedStations);
        Map<String, List<Station>> map = new LinkedHashMap<>();
        for (int i = 0, j = arrayList.size(); i < arrayList.size(); j--) {
            List<Station> subList = arrayList.subList(i, j);
            for (Map.Entry<String, List<Station>> entry : subwayMessage.entrySet()) {
                if (entry.getValue().containsAll(subList)) {
                    map.put(entry.getKey(), subList);
                    i = j;
                    j = arrayList.size() + 1;
                }
            }
        }

        // 输出结果到文件
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(destFilePath));
            int totalStaion = allPassedStations.size(); // 包括出发与目的站点
            writer.write(totalStaion + "\r\n");
            for (Map.Entry<String, List<Station>> entry : map.entrySet()) {
                writer.write(entry.getKey() + "\r\n");
                for (Station station : entry.getValue()) {
                    writer.write(station.getName() + "\r\n");
                }
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 公共方法, 读取地铁文件
     *
     * @return
     */
    private static List<String> getLineData() {
        // 按行读取文件
        List<String> lineData = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath),"UTF-8"));
            String s;
            while ((s = br.readLine()) != null) {
                lineData.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lineData;
    }
}
