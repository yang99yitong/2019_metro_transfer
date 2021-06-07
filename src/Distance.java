import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Distance {

    // 记录已经分析过的站点
    private List<Station> outList = new ArrayList<>();

    /**
     * 计算从start站到end站的最短经过路径
     *
     * @param start
     * @param end
     */
    public void calculate(Station start, Station end) {
        if (outList.size() == SubwayMessage.getTotalStaion()) {
            return;
        }

        if (!outList.contains(start)) {
            outList.add(start);
        }

        // 如果起点站的OrderSetMap为空，则第一次用起点站的前后站点初始化之
        if (start.getOrderSetMap().isEmpty()) {
            List<Station> Linkedstations = getAllLinkedStations(start);
            for (Station s : Linkedstations) {
                start.getAllPassedStations(s).add(s);
            }
        }

        // 获取距离起点站start最近的一个站（有多个的话，随意取一个）
        Station parent = getShortestPath(start);
        if (parent == end) {
            return;
        }

        // 看这个最近的站点的出边
        for (Station child : getAllLinkedStations(parent)) {
            if (outList.contains(child)) {
                continue;
            }
            int shortestPath = (start.getAllPassedStations(parent).size() - 1) + 1; // 前面这个1表示计算路径需要去除自身站点，后面这个1表示增加了1站距离
            if (start.getAllPassedStations(child).contains(child)) { // 如果start已经计算过到此child的经过距离，那么比较出最小的距离
                if ((start.getAllPassedStations(child).size() - 1) > shortestPath) {
                    // 重置start到周围各站的最小路径
                    start.getAllPassedStations(child).clear();
                    start.getAllPassedStations(child).addAll(start.getAllPassedStations(parent));
                    start.getAllPassedStations(child).add(child);
                }
            } else { // 如果start还没有计算过到此child的经过距离
                start.getAllPassedStations(child).addAll(start.getAllPassedStations(parent));
                start.getAllPassedStations(child).add(child);
            }
        }
        outList.add(parent);
        calculate(start, end); // 重复计算，往外面站点扩展
    }

    /**
     * 取参数station到各个站的最短距离，相隔1站，距离为1，依次类推
     * 
     * @param station
     * @return
     */
    private Station getShortestPath(Station station) {
        int minPatn = Integer.MAX_VALUE;
        Station resultStation = null;
        for (Station s : station.getOrderSetMap().keySet()) {
            if (outList.contains(s)) {
                continue;
            }
            // 参数station到s所经过的所有站点的集合
            LinkedHashSet<Station> set = station.getAllPassedStations(s); 
            if (set.size() < minPatn) {
                minPatn = set.size();
                resultStation = s;
            }
        }
        return resultStation;
    }

    /**
     * 获取参数station直接相连的所有站，包括交叉线上面的站
     * 
     * @param station
     * @return
     */
    private List<Station> getAllLinkedStations(Station station) {
        List<Station> linkedStaions = new ArrayList<>();
        for (List<Station> line : SubwayMessage.getLineSet()) {
            if (line.contains(station)) { // 如果某一条线包含了此站，注意由于重写了hashcode方法，只要name相同，即认为是同一个对象
                Station s = line.get(line.indexOf(station));
                if (s.prev != null) {
                    linkedStaions.add(s.prev);
                }
                if (s.next != null) {
                    linkedStaions.add(s.next);
                }
            }
        }
        return linkedStaions;
    }
}
