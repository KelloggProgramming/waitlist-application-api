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

    @GetMapping
    public List<TableRecord> getAllAvailableTables(@RequestParam(name = "status", required = false) TableStatus status) {

        return tableService.searchTables(status);
    }

    @GetMapping("/{id}")
    public TableRecord getAllAvailableTables(@PathVariable("id") Integer tableId) {
        return tableService.findTableById(tableId);
    }

    @PutMapping("/{id}/status/{tableStatus}")
    public TableRecord setTableStatusById(@PathVariable("id") Integer tableId, @PathVariable("tableStatus") TableStatus tableStatus) {
        return tableService.setTableStatus(tableId, tableStatus);
    }

    @PostMapping
    public TableRecord createTable(@RequestBody TableRecord tableRecord) {
        return tableService.createTableRecord(tableRecord);
    }

    @PutMapping
    public TableRecord updateTable(@RequestBody TableRecord tableRecord) {
        return tableService.updateTableRecord(tableRecord);
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
