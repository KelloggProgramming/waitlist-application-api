package rip.jack.waitlistapi.controller;

import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TablesController {
    private final TableRepository tableRepository;
    private final TableService tableService;

    @GetMapping("/available")
    public List<TableRecord> getAvailableTables() {
        return tableRepository.findAll();
    }

    @GetMapping("/in-use")
    public List<Table> getInUseTables() {
        return new ArrayList<>(Collections.singletonList(
                new Table(3, "small boi", LocalDate.of(2023, 1, 23))
        ));
    }

    @PutMapping("/create/{table_id}")
    public ResponseEntity getInUseTables(@PathVariable("table_id") Integer tableId) {
        tableService.createTable(tableId);
        return new ResponseEntity(HttpStatus.OK);



//        return new ArrayList<>(Collections.singletonList(
//                new Table(3, "small boi", LocalDate.of(2023, 1, 23))
//        ));
    }
}
