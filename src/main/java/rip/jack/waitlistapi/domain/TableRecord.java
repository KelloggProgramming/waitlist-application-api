package rip.jack.waitlistapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;
import rip.jack.waitlistapi.enums.TableStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tables",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tableNumber"}, name = "unique_table_number")
})
public class TableRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int tableNumber;

    @Column(length = 30)
    private String displayName;

    @Column
    private int numberOfSeats;

    @ManyToOne
    private TableType tableType;

    private TableStatus status = TableStatus.UNKNOWN;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime statusUpdated;

    @ManyToMany(mappedBy = "tables")
    private Collection<ReservationRecord> reservations;

    public TableRecord(rip.jack.waitlistapi.model.Table table) {
        this.setId(table.getId());
        this.setTableNumber(table.getTableNumber());
        this.setNumberOfSeats(table.getNumberOfSeats());

    }

    public void setStatus(TableStatus status) {
        this.status = status;
        this.setStatusUpdated(LocalDateTime.now(ZoneId.of("UTC")));
    }
}
