package rip.jack.waitlistapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.domain.TableType;
import rip.jack.waitlistapi.enums.TableStatus;
import rip.jack.waitlistapi.model.Table;
import rip.jack.waitlistapi.repository.TableRepository;
import rip.jack.waitlistapi.repository.TableTypeRepository;
import rip.jack.waitlistapi.service.TableService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TablesController {
    private final TableRepository tableRepository;
    private final TableTypeRepository tableTypeRepository;

    private final TableService tableService;

    @GetMapping("")
    public List<TableRecord> getAllAvailableTables() {
        return tableRepository.findAll();
    }

    @GetMapping("/available")
    public List<TableRecord> getAvailableTables() {
        return tableRepository.findTableRecordsByStatusIs(TableStatus.AVAILABLE, Sort.by(Sort.Direction.ASC));
    }

    @GetMapping("/available/{id}")
    public TableRecord setTableAvailable(@PathVariable("id") UUID uuid) {
        return tableService.setTableAvailable(uuid);
    }

    @GetMapping("/in-use")
    public List<TableRecord> getInUseTables() {
        return tableRepository.findTableRecordsByStatusIs(TableStatus.INUSE, Sort.by(Sort.Direction.ASC));
    }

    @GetMapping("/in-use/{id}")
    public TableRecord setTableInUse(@PathVariable("id") UUID uuid) {
        return tableService.setTableInUse(uuid);
    }

    @PutMapping("/create/{table_id}")
    public TableRecord getInUseTables(@PathVariable("table_id") Integer tableId) {
        return tableService.createTable(tableId);
    }

    @PostMapping
    public TableRecord createTable(@RequestBody TableRecord tableRecord) {
//        new TableRecord(table);
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
