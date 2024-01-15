package rip.jack.waitlistapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.enums.TableStatus;
import rip.jack.waitlistapi.repository.TableRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Profile("it")
public class TableServiceTest {
    @Mock
    private TableRepository mockTableRepository;

    @InjectMocks
    private TableService classUnderTest;

    @Test
    void setTableStatusTest() {
        Integer tableId = 1;

        TableRecord setupTableRecord = new TableRecord();
        setupTableRecord.setId(tableId);
        setupTableRecord.setStatus(TableStatus.UNKNOWN);

        when(mockTableRepository.findById(tableId)).thenReturn(Optional.of(setupTableRecord));
        when(mockTableRepository.save(setupTableRecord)).thenReturn(null);

        var actualTableRecord = classUnderTest.setTableStatus(tableId, TableStatus.RESERVED);

        assertEquals(tableId, actualTableRecord.getId());
        assertEquals(TableStatus.RESERVED, actualTableRecord.getStatus());
        verify(mockTableRepository, times(1)).findById(tableId);
        verify(mockTableRepository, times(1)).save(setupTableRecord);
    }

    @Test
    void setTableStatus_noRecordFound_Test() {
        Integer tableId = 1;
        when(mockTableRepository.findById(tableId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> classUnderTest.setTableStatus(tableId, TableStatus.RESERVED));
        verify(mockTableRepository, times(1));
    }
}
