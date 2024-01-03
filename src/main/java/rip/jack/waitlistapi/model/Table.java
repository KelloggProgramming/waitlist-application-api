package rip.jack.waitlistapi.model;

import lombok.Data;
import rip.jack.waitlistapi.domain.ReservationRecord;
import rip.jack.waitlistapi.domain.TableType;
import rip.jack.waitlistapi.enums.TableStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Data
public class Table {
    private UUID id;

    private int tableNumber;

    private int numberOfSeats;

    private TableType tableType;

    private TableStatus status = TableStatus.UNKNOWN;

    private LocalDateTime statusUpdated;

    private Collection<ReservationRecord> reservations;

}
