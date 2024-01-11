package rip.jack.waitlistapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import rip.jack.waitlistapi.enums.TableStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tables",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tableNumber"}, name = "unique_table_number")
        })
public class TableRecord {
    @Id
    @GeneratedValue
    private Integer id;

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

    public void setStatus(TableStatus status) {
        this.status = status;
        this.setStatusUpdated(LocalDateTime.now(ZoneId.of("UTC")));
    }
}
