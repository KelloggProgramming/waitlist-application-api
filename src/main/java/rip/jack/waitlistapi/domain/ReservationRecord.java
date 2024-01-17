package rip.jack.waitlistapi.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Primary;
import rip.jack.waitlistapi.repository.TableRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "reservations")
public class ReservationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime scheduledTime;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "reservations_tables",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "table_id", referencedColumnName = "id")
    )
    private List<TableRecord> tables;
}
