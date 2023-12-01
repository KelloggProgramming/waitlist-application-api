package rip.jack.waitlistapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;
import rip.jack.waitlistapi.enums.TableStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@Table(name = "tables")
public class TableRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private int tableNumber;

    //    @Column(name = "tableType")
//    private String tableType;

    private TableStatus status = TableStatus.UNKNOWN;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime statusUpdated;

    @ManyToMany(mappedBy = "tables")
    private Collection<ReservationRecord> reservations;

    public void setStatus(TableStatus status) {
        this.status = status;
        this.setStatusUpdated(LocalDateTime.now(ZoneId.of("UTC")));
    }
}
