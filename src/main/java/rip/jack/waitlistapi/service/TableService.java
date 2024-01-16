package rip.jack.waitlistapi.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.enums.TableStatus;
import rip.jack.waitlistapi.repository.TableRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    public TableRecord findTableRecordById(Integer tableId) {
        Optional<TableRecord> optionalTableRecord = tableRepository.findById(tableId);

        if (optionalTableRecord.isEmpty()) {
            throw new EntityNotFoundException("Could not find original table record to update");
        }

        return optionalTableRecord.get();
    }

    public TableRecord createTableRecord(TableRecord tableRecord) {
        tableRecord.setStatusUpdated(LocalDateTime.now(ZoneId.of("UTC")));
        tableRecord.setId(null);

        tableRepository.save(tableRecord);

        log.info("Created table ID {}", tableRecord.getId());

        return tableRecord;
    }

    public TableRecord updateTableRecord(TableRecord editedTableRecord) {
        TableRecord dbTableRecord = findTableRecordById(editedTableRecord.getId());


        //TODO Find a much better way to do this
        if(dbTableRecord.getStatus().equals(editedTableRecord.getStatus())) {
            editedTableRecord.setStatusUpdated(dbTableRecord.getStatusUpdated());
        }else {
            editedTableRecord.setStatusUpdated(LocalDateTime.now(ZoneId.of("UTC")));
        }

        tableRepository.save(editedTableRecord);

        log.info("Updated table ID {}", editedTableRecord.getId());

        return editedTableRecord;
    }

    public TableRecord setTableStatus(Integer tableId, TableStatus status) {
        TableRecord tableRecord = findTableRecordById(tableId);

        tableRecord.updateStatus(status);

        tableRepository.save(tableRecord);

        log.info("Updated table status {}:{}", tableRecord.getId(), status);

        return tableRecord;
    }

}
