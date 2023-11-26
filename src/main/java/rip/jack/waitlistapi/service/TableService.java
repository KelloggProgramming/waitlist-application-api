package rip.jack.waitlistapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rip.jack.waitlistapi.domain.ReservationRecord;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.model.Table;
import rip.jack.waitlistapi.repository.TableRepository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    public TableRecord createTable(Integer tableNumber) {
        TableRecord newTableRecord = new TableRecord();
        newTableRecord.setTableNumber(tableNumber);

        tableRepository.save(newTableRecord);

        return newTableRecord;
    }

    public TableRecord setTableInUse(UUID tableUuid) {
        Optional<TableRecord> optionalTableRecord = tableRepository.findById(tableUuid);

        if(optionalTableRecord.isEmpty()) {
            throw new EntityNotFoundException("Could not find original table record to update");
        }

        TableRecord tableRecord = optionalTableRecord.get();

        tableRecord.setInUse(true);
        tableRecord.setInUseStartTime(LocalDateTime.now(ZoneId.of("UTC")));

        tableRepository.save(tableRecord);

        return tableRecord;
    }

    public TableRecord setTableAvailable(UUID tableUuid) {
        Optional<TableRecord> optionalTableRecord = tableRepository.findById(tableUuid);

        if(optionalTableRecord.isEmpty()) {
            throw new EntityNotFoundException("Could not find original table record to update");
        }

        TableRecord tableRecord = optionalTableRecord.get();

        tableRecord.setInUse(false);
        tableRecord.setInUseStartTime(null);

        tableRepository.save(tableRecord);

        return tableRecord;
    }
}
