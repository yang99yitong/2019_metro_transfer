import java.util.*;

public class SubwayMessage {

    // Map<线路, 站点>
    private static Map<String, List<Station>> subwayMessage;

    // 所有线集合
    private static Set<List<Station>> lineSet;

    // 站点总数
    private static int totalCount;

    /**
     * 获取所有地铁线信息(包括地铁线名)
     *
     * @param lineData
     * @return
     */
    public static Map<String, List<Station>> getSubwayMessage(List<String> lineData){
        if (subwayMessage == null) {
            subwayMessage = new HashMap<>();
            for (String line : lineData) {
                String[] split = line.split(":");
                String lineNameStr = split[0];
                String[] stations = split[1].split("->");
                List<Station> stationList = new ArrayList<>();
                for (String station : stations) {
                    stationList.add(new Station(station));
                }
                subwayMessage.put(lineNameStr, stationList);

                // 站点之间的关系
                for (int i = 0; i < subwayMessage.get(lineNameStr).size(); i++) {
                    if (i < subwayMessage.get(lineNameStr).size() - 1) {
                        subwayMessage.get(lineNameStr).get(i).next = subwayMessage.get(lineNameStr).get(i + 1);
                        subwayMessage.get(lineNameStr).get(i + 1).prev = subwayMessage.get(lineNameStr).get(i);
                    }
                }
            }
        }

        return subwayMessage;
    }

    /**
     * 获取所有地铁线路(不包括地铁线名)
     *
     * @return
     */
    public static Set<List<Station>> getLineSet(){
        if (lineSet == null) {
            lineSet = new HashSet<>();
            lineSet.addAll(subwayMessage.values());
        }

        return lineSet;
    }

    /**
     * 获取所有地铁站的数量
     *
     * @return
     */
    public static int getTotalStaion() {
        if (totalCount == 0) {
            for (List<Station> stationList : getLineSet()) {
                totalCount += stationList.size();
            }
        }
        return totalCount;
    }
}
