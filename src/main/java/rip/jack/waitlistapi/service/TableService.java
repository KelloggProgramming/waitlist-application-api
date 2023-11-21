package rip.jack.waitlistapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.repository.TablesRepository;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TablesRepository tablesRepository;
    public void createTable(Integer tableNumber) {
        TableRecord newTableRecord = new TableRecord();
        newTableRecord.setTableNumber(tableNumber);

        tablesRepository.save(newTableRecord);
    }
}
