package rip.jack.waitlistapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import rip.jack.waitlistapi.model.Table;

import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TableType {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private int numberOfSeats;

    @Column
    private String notes;

    @Column(nullable = false)
    private int maxJoinLength = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "tableType")
    private Collection<TableRecord> tables;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TableType tableType = (TableType) o;
        return getId() != null && Objects.equals(getId(), tableType.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
