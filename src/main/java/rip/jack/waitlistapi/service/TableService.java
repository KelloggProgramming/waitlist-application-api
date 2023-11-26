package rip.jack.waitlistapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rip.jack.waitlistapi.domain.ReservationRecord;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.repository.TableRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    public void createTable(Integer tableNumber) {
        TableRecord newTableRecord = new TableRecord();
        newTableRecord.setTableNumber(tableNumber);

        tableRepository.save(newTableRecord);
    }

    public void createReservation() {
        ReservationRecord reservation = new ReservationRecord();
        reservation.setName("Jack Bob Jo");
        reservation.setScheduledTime(LocalDateTime.of(2023,11,23,4,13,30));

        TableRecord table = new TableRecord();
        table.setId(UUID.fromString("63695070-8c51-449e-8593-0ca7012f3b66"));

        reservation.setTables(
                Collections.singletonList(
                        table
                )
        );
    }
}
