package dotx.lib.masking;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Mask {
    private List<String> filter;
    private String mask = "******";
    private Hash hasher;

    public Mask(List<String> filter, Hash hasher) {
        this.filter = filter;
        this.hasher = hasher;
    }

    public Mask(List<String> filter) {
        this.filter = filter;
    }

    public List list(List list) {
        if ( ! initialize()) {
            return list;
        }

        list.forEach(l -> {
            if (l instanceof Map) {
                map((Map) l);
            } else if (l instanceof List) {
                list((List) l);
            }
        });

        return list;
    }

    public Map map(Map m) {
        log.info("Map " + m);
        if ( ! initialize()) {
            return m;
        }

        Set keys = m.keySet();
        keys.iterator().forEachRemaining(k -> {
            String key = String.valueOf(k);
            Object obj = m.get(key);
            if (obj instanceof Map) {
                map((Map) obj);
            } else if (obj instanceof List) {
                // this logic is for List
                m.put(key, list((List) obj));
            } else {
                // this is the actual key-pair
                mask(m, key, obj);
            }
        });

        return m;
    }

    private void mask(Map map, String key, Object value) {
        filter.forEach(f -> {
            if (key.toLowerCase().contains(f.toLowerCase())) {
                String hash = hasher.execute(String.valueOf(value));
                map.put(key, hash);
            }
        });
    }

    /**
     * Don't consume
     */
    private boolean initialize() {
        if (null == filter || filter.size() == 0) {
            return false;
        }

        if (null == hasher) {
            hasher = str -> "******";
        }

        return true;
    }

}
