package rip.jack.waitlistapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rip.jack.waitlistapi.domain.TableRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface TableRepository extends JpaRepository<TableRecord, UUID> {

    List<TableRecord> findAll(Sort sort);

    Optional<TableRecord> findById(UUID uuid);

    List<TableRecord> findTableRecordsByInUseIsTrue(Sort sort);

    List<TableRecord> findTableRecordsByInUseIsFalse();

    @Modifying
    @Query("update TableRecord t set t.inUse = :inUse where t.id = :id")
    void updateTableRecordById(UUID id, boolean inUse);
}
