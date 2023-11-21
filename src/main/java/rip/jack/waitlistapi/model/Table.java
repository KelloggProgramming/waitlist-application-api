package rip.jack.waitlistapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Table {
    private int tableNumber;
    private String tableType;
    private LocalDate inUseStartTime;
}
