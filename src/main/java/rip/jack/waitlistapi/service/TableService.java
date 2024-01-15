package rip.jack.waitlistapi.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.enums.TableStatus;
import rip.jack.waitlistapi.repository.TableRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    public List<TableRecord> searchTables(TableStatus status) {
        if (status == null) {
            return tableRepository.findAll();
        } else {
            return tableRepository.findTableRecordsByStatusIs(status);
        }
    }

    public TableRecord findTableById(Integer tableId) {
        Optional<TableRecord> optionalTableRecord = tableRepository.findById(tableId);

        if(optionalTableRecord.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return optionalTableRecord.get();
    }

    public TableRecord createTableRecord(TableRecord tableRecord) {
        tableRecord.setStatusUpdated(LocalDateTime.now());
        tableRecord.setId(null);

        tableRepository.save(tableRecord);

        log.info("Created table ID {}", tableRecord.getId());

        return tableRecord;
    }

    public TableRecord updateTableRecord(TableRecord tableRecord) {
        tableRecord.setNew(false);

        assert tableRecord.getId() != null;
        Optional<TableRecord> optionalDbTableRecord = tableRepository.findById(tableRecord.getId());

        if(optionalDbTableRecord.isEmpty()) {
            throw new EntityNotFoundException("Could not find original table to update.");
        }

        TableRecord dbTableRecord = optionalDbTableRecord.get();

        if(dbTableRecord.getStatus().equals(tableRecord.getStatus())) {
            tableRecord.setStatusUpdated(dbTableRecord.getStatusUpdated());
        }else {
            tableRecord.setStatusUpdated(LocalDateTime.now());
        }

        tableRepository.save(tableRecord);

        log.info("Updated table ID {}", tableRecord.getId());

        return tableRecord;
    }

    public TableRecord setTableStatus(Integer tableId, TableStatus status) {
        Optional<TableRecord> optionalTableRecord = tableRepository.findById(tableId);

        if (optionalTableRecord.isEmpty()) {
            throw new EntityNotFoundException("Could not find original table record to update");
        }

        TableRecord tableRecord = optionalTableRecord.get();

        tableRecord.setStatus(status);

        tableRepository.save(tableRecord);

        log.info("Updated table status {}:{}", tableRecord.getId(), status);

        return tableRecord;
    }

}
