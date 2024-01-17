package rip.jack.waitlistapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rip.jack.waitlistapi.domain.ReservationRecord;
import rip.jack.waitlistapi.domain.TableRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<ReservationRecord, UUID> {

}
