package rip.jack.waitlistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.jack.waitlistapi.domain.TableRecord;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableRecord, Long> {

    List<TableRecord> findAll();
}
