package rip.jack.waitlistapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import rip.jack.waitlistapi.domain.TableRecord;
import rip.jack.waitlistapi.enums.TableStatus;
import rip.jack.waitlistapi.repository.TableRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Profile("it")
public class TableServiceTest {
    @Mock
    private TableRepository mockTableRepository;

    @InjectMocks
    private TableService classUnderTest;

    @Test
    void createTableTest() {
        TableRecord actualTableRecord = classUnderTest.createTable(1);

        when(mockTableRepository.save(any(TableRecord.class))).thenReturn(null);

        verify(mockTableRepository, times(1)).save(any(TableRecord.class));
        assert actualTableRecord.getTableNumber() == 1;
        assert actualTableRecord.getStatus() == TableStatus.UNKNOWN;
    }

    @Test
    void setTableStatusTest() {
        UUID tableUuid = UUID.randomUUID();

        TableRecord setupTableRecord = new TableRecord();
        setupTableRecord.setId(tableUuid);
        setupTableRecord.setStatus(TableStatus.UNKNOWN);

        when(mockTableRepository.findById(tableUuid)).thenReturn(Optional.of(setupTableRecord));
        when(mockTableRepository.save(setupTableRecord)).thenReturn(null);

        var actualTableRecord = classUnderTest.setTableStatus(tableUuid, TableStatus.RESERVED);

        assert actualTableRecord.getId() == tableUuid;
        assert actualTableRecord.getStatus() == TableStatus.RESERVED;
        verify(mockTableRepository, times(1)).findById(tableUuid);
        verify(mockTableRepository, times(1)).save(setupTableRecord);
    }

    @Test
    void setTableStatus_noRecordFound_Test() {
        UUID tableUuid = UUID.randomUUID();
        when(mockTableRepository.findById(tableUuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> classUnderTest.setTableStatus(tableUuid, TableStatus.RESERVED));
        verify(mockTableRepository, times(1));
    }
}
