package rip.jack.waitlistapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.enums.TableStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface TableRepository extends JpaRepository<TableRecord, Integer> {

    List<TableRecord> findTableRecordsByStatusIs(TableStatus status);

    @Modifying
    @Query("update TableRecord t set t.status = :tableStatus where t.id = :id")
    void updateTableRecordById(UUID id, TableStatus tableStatus);

}
