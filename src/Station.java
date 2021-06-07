import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class Station {
    private String name; //地铁站名称，假设具备唯一性

    public Station prev; //本站在lineNo线上面的前一个站

    public Station next; //本站在lineNo线上面的后一个站

    public Station(String name) {
        this.name = name;
    }

    // 本站到某一个目标站(key)所经过的所有站集合(value)，保持前后顺序
    private Map<Station, LinkedHashSet<Station>> orderSetMap = new HashMap<Station, LinkedHashSet<Station>>();

    public LinkedHashSet<Station> getAllPassedStations(Station station) {
        if (orderSetMap.get(station) == null) {
            LinkedHashSet<Station> set = new LinkedHashSet<>();
            set.add(this);
            orderSetMap.put(station, set);
        }
        return orderSetMap.get(station);
    }

    public Map<Station, LinkedHashSet<Station>> getOrderSetMap() {
        return orderSetMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Station) {
            Station s = (Station) obj;
            boolean sContains = s.getName().contains("\\(");
            if (sContains) {
               return s.getName().split("\\(")[0].equals(this.getName());
            } else  {
                return s.getName().equals(this.getName());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (this.getName().contains("(")) {
            return this.getName().split("\\(")[0].hashCode();
        } else {
            return this.getName().hashCode();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
