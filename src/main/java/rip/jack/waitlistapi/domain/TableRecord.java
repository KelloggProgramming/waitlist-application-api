package rip.jack.waitlistapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private boolean inUse = false;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime inUseStartTime;

    @ManyToMany(mappedBy = "tables")
    private Collection<ReservationRecord> reservations;
}
