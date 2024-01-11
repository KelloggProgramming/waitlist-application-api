package rip.jack.waitlistapi.service;

import rip.jack.waitlistapi.domain.ReservationRecord;
import rip.jack.waitlistapi.domain.TableRecord;

import java.time.LocalDateTime;
import java.util.Collections;

public class ReservationService {
    public void createReservation() {
        ReservationRecord reservation = new ReservationRecord();
        reservation.setName("Jack Bob Jo");
        reservation.setScheduledTime(LocalDateTime.of(2023, 11, 23, 4, 13, 30));

        TableRecord table = new TableRecord();
//        table.setId(UUID.fromString("63695070-8c51-449e-8593-0ca7012f3b66"));

        reservation.setTables(
                Collections.singletonList(
                        table
                )
        );
    }
}
