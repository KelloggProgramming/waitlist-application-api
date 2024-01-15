package rip.jack.waitlistapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.domain.Persistable;
import org.yaml.snakeyaml.events.Event;
import rip.jack.waitlistapi.enums.TableStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "tables",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tableNumber"}, name = "unique_table_number")
        })
public class TableRecord implements Persistable<Integer> {
    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

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
    @ToString.Exclude
    private Collection<ReservationRecord> reservations;

    public void setStatus(TableStatus status) {
        this.status = status;
        this.setStatusUpdated(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TableRecord that = (TableRecord) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
