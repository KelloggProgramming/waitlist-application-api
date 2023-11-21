package rip.jack.waitlistapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "tables")
public class TableRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private int tableNumber;
//    @Column(name = "tableType")
//    private String tableType;
    private boolean inUse;
}
