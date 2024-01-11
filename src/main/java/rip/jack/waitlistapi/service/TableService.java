package rip.jack.waitlistapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.enums.TableStatus;
import rip.jack.waitlistapi.repository.TableRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    public TableRecord createTable(Integer tableNumber) {
        TableRecord newTableRecord = new TableRecord();
        newTableRecord.setTableNumber(tableNumber);
        newTableRecord.setStatus(TableStatus.UNKNOWN);

        tableRepository.save(newTableRecord);

        return newTableRecord;
    }

    public TableRecord setTableStatus(Integer tableId, TableStatus status) {
        Optional<TableRecord> optionalTableRecord = tableRepository.findById(tableId);

        if (optionalTableRecord.isEmpty()) {
            throw new EntityNotFoundException("Could not find original table record to update");
        }

        TableRecord tableRecord = optionalTableRecord.get();

        tableRecord.setStatus(status);

        tableRepository.save(tableRecord);

        return tableRecord;
    }

}
