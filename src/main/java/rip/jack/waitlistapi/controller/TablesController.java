package rip.jack.waitlistapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.model.Table;
import rip.jack.waitlistapi.repository.TableRepository;
import rip.jack.waitlistapi.service.TableService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TablesController {
    private final TableRepository tableRepository;

    private final TableService tableService;

    @GetMapping("")
    public List<TableRecord> getAllAvailableTables() {
        return tableRepository.findAll(Sort.by(Sort.Direction.ASC, "inUseStartTime"));
    }

    @GetMapping("/available")
    public List<TableRecord> getAvailableTables() {
        return tableRepository.findTableRecordsByInUseIsFalse();
    }

    @GetMapping("/available/{id}")
    public TableRecord setTableAvailable(@PathVariable("id") UUID uuid) {
        return tableService.setTableAvailable(uuid);
    }

    @GetMapping("/in-use")
    public List<TableRecord> getInUseTables() {
        return tableRepository.findTableRecordsByInUseIsTrue(Sort.by(Sort.Direction.ASC, "inUseStartTime"));
    }

    @GetMapping("/in-use/{id}")
    public TableRecord setTableInUse(@PathVariable("id") UUID uuid) {
        return tableService.setTableInUse(uuid);
    }

    @PutMapping("/create/{table_id}")
    public TableRecord getInUseTables(@PathVariable("table_id") Integer tableId) {
        return tableService.createTable(tableId);
    }
}
