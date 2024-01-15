package rip.jack.waitlistapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.domain.TableType;
import rip.jack.waitlistapi.enums.TableStatus;
import rip.jack.waitlistapi.repository.TableRepository;
import rip.jack.waitlistapi.repository.TableTypeRepository;
import rip.jack.waitlistapi.service.TableService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TablesController {
    private final TableRepository tableRepository;

    private final TableTypeRepository tableTypeRepository;

    private final TableService tableService;

    @GetMapping("")
    public List<TableRecord> getAllAvailableTables(@RequestParam(name = "status", required = false) TableStatus status) {

        return tableService.searchTables(status);
    }

    @GetMapping("/available")
    public List<TableRecord> getAvailableTables() {
        return tableRepository.findTableRecordsByStatusIs(TableStatus.AVAILABLE, Sort.by(Sort.Direction.ASC));
    }

    @GetMapping("/available/{id}")
    public TableRecord setTableAvailable(@PathVariable("id") Integer tableId) {
        return tableService.setTableStatus(tableId, TableStatus.AVAILABLE);
    }

    @GetMapping("/in-use")
    public List<TableRecord> getInUseTables() {
        return tableRepository.findTableRecordsByStatusIs(TableStatus.INUSE, Sort.by(Sort.Direction.ASC));
    }

    @GetMapping("/in-use/{id}")
    public TableRecord setTableInUse(@PathVariable("id") Integer tableId) {
        return tableService.setTableStatus(tableId, TableStatus.INUSE);
    }

    @PutMapping("/create/{table_id}")
    public TableRecord getInUseTables(@PathVariable("table_id") Integer tableId) {
        return tableService.createTable(tableId);
    }

    @PostMapping
    public TableRecord createTable(@RequestBody TableRecord tableRecord) {
        tableRecord.setStatusUpdated(LocalDateTime.now());
        return tableRepository.save(tableRecord);
    }

    @GetMapping("/types")
    public List<TableType> listTableTypes() {
        return tableTypeRepository.findAll();
    }

    @PostMapping("/types")
    public TableType createTableType(@RequestBody TableType tableType) {
        return tableTypeRepository.save(tableType);
    }
}
