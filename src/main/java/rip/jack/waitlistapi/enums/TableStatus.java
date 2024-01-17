package rip.jack.waitlistapi.enums;

import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;

import java.util.HashMap;
import java.util.Map;

public enum TableStatus {

    AVAILABLE(1),
    INUSE(2),
    RESERVED(3),
    DISABLED(11),
    UNKNOWN(99);

    private static final Map<Integer, TableStatus> BY_NAME = new HashMap<>();

    static {
        for(var status : values()) {
            BY_NAME.put(status.id, status);
        }
    }
    private int id;

    TableStatus(int id) {
        this.id = id;
    }

    int getId() {
        return this.id;
    }

    public static TableStatus valueOf(int id) {
        return BY_NAME.get(id);
    }

}
