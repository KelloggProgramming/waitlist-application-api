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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void searchTables_NullStatus_Test() {
        classUnderTest.searchTables(null);

        verify(mockTableRepository, times(1)).findAll();
    }

    @Test
    void searchTables_AvailableStatus_Test() {
        classUnderTest.searchTables(TableStatus.AVAILABLE);

        verify(mockTableRepository, times(1)).findTableRecordsByStatusIs(TableStatus.AVAILABLE);
    }

    @Test
    void findTableRecordById_validId_test() {
        var expectedTableId = 1;
        var expectedTableRecord = new TableRecord();
        expectedTableRecord.setId(expectedTableId);

        when(mockTableRepository.findById(expectedTableId)).thenReturn(Optional.of(expectedTableRecord));

        var actualTableRecord = classUnderTest.findTableRecordById(1);

        assertEquals(expectedTableRecord, actualTableRecord);
    }

    @Test
    void findTableRecordById_NullId_test() {
        when(mockTableRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            classUnderTest.findTableRecordById(1);
        });
    }

    @Test
    void createTableRecord_Test() {
        Integer tableId = 1;

        LocalDateTime requestStatusUpdatedTime = LocalDateTime.now(ZoneId.of("UTC"));
        TableRecord requestTableRecord = new TableRecord();
        requestTableRecord.setId(tableId);
        requestTableRecord.updateStatus(TableStatus.AVAILABLE);
        requestTableRecord.setStatusUpdated(requestStatusUpdatedTime);

        when(mockTableRepository.save(requestTableRecord)).thenReturn(null);


        var actualTableRecord = classUnderTest.createTableRecord(requestTableRecord);

        assertNull(actualTableRecord.getId());
        assertEquals(TableStatus.AVAILABLE, actualTableRecord.getStatus());
        assertTrue(requestStatusUpdatedTime.isBefore(actualTableRecord.getStatusUpdated()));

        verify(mockTableRepository, times(1)).save(requestTableRecord);
    }

    @Test
    void updateTableRecord_ValidTableRecord_NewStatus_Test() {
        Integer tableId = 1;

        LocalDateTime mockedDbStatusUpdatedTime = LocalDateTime.now(ZoneId.of("UTC"));
        TableRecord mockedDatabaseTableRecord = new TableRecord();
        mockedDatabaseTableRecord.setId(tableId);
        mockedDatabaseTableRecord.updateStatus(TableStatus.UNKNOWN);
        mockedDatabaseTableRecord.setStatusUpdated(mockedDbStatusUpdatedTime);


        LocalDateTime requestStatusUpdatedTime = LocalDateTime.now(ZoneId.of("UTC"));
        TableRecord requestTableRecord = new TableRecord();
        requestTableRecord.setId(tableId);
        requestTableRecord.updateStatus(TableStatus.AVAILABLE);
        requestTableRecord.setStatusUpdated(requestStatusUpdatedTime);


        when(mockTableRepository.findById(tableId)).thenReturn(Optional.of(mockedDatabaseTableRecord));
        when(mockTableRepository.save(requestTableRecord)).thenReturn(null);


        var actualTableRecord = classUnderTest.updateTableRecord(requestTableRecord);

        assertEquals(tableId, actualTableRecord.getId());
        assertEquals(TableStatus.AVAILABLE, actualTableRecord.getStatus());
        assertTrue(mockedDbStatusUpdatedTime.isBefore(actualTableRecord.getStatusUpdated()));

        verify(mockTableRepository, times(1)).findById(tableId);
        verify(mockTableRepository, times(1)).save(mockedDatabaseTableRecord);
    }

    @Test
    void updateTableRecord_ValidTableRecord_SameStatus_Test() {
        Integer tableId = 1;

        LocalDateTime mockedDbStatusUpdatedTime = LocalDateTime.now(ZoneId.of("UTC"));
        TableRecord mockedDatabaseTableRecord = new TableRecord();
        mockedDatabaseTableRecord.setId(tableId);
        mockedDatabaseTableRecord.updateStatus(TableStatus.UNKNOWN);
        mockedDatabaseTableRecord.setStatusUpdated(mockedDbStatusUpdatedTime);


        LocalDateTime requestStatusUpdatedTime = LocalDateTime.now(ZoneId.of("UTC"));
        TableRecord requestTableRecord = new TableRecord();
        requestTableRecord.setId(tableId);
        requestTableRecord.updateStatus(TableStatus.UNKNOWN);
        requestTableRecord.setStatusUpdated(requestStatusUpdatedTime);


        when(mockTableRepository.findById(tableId)).thenReturn(Optional.of(mockedDatabaseTableRecord));
        when(mockTableRepository.save(requestTableRecord)).thenReturn(null);


        var actualTableRecord = classUnderTest.updateTableRecord(requestTableRecord);

        assertEquals(tableId, actualTableRecord.getId());
        assertEquals(TableStatus.UNKNOWN, actualTableRecord.getStatus());
        assertTrue(mockedDbStatusUpdatedTime.isEqual(actualTableRecord.getStatusUpdated()));

        verify(mockTableRepository, times(1)).findById(tableId);
        verify(mockTableRepository, times(1)).save(mockedDatabaseTableRecord);
    }

    @Test
    void updateTableRecord_noRecordFound_Test() {
        Integer tableId = 1;
        TableRecord expectedTableRecord = new TableRecord();
        expectedTableRecord.setId(tableId);

        when(mockTableRepository.findById(tableId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> classUnderTest.updateTableRecord(expectedTableRecord));
        verify(mockTableRepository, times(1)).findById(tableId);
    }

    @Test
    void setTableStatus_DifferentStatus_Test() {
        Integer tableId = 1;

        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("UTC"));

        TableRecord setupTableRecord = new TableRecord();
        setupTableRecord.setId(tableId);
        setupTableRecord.updateStatus(TableStatus.UNKNOWN);
        setupTableRecord.setStatusUpdated(startTime);

        when(mockTableRepository.findById(tableId)).thenReturn(Optional.of(setupTableRecord));
        when(mockTableRepository.save(setupTableRecord)).thenReturn(null);

        var actualTableRecord = classUnderTest.setTableStatus(tableId, TableStatus.RESERVED);

        assertEquals(tableId, actualTableRecord.getId());
        assertEquals(TableStatus.RESERVED, actualTableRecord.getStatus());
        assertTrue(startTime.isBefore(actualTableRecord.getStatusUpdated()));

        verify(mockTableRepository, times(1)).findById(tableId);
        verify(mockTableRepository, times(1)).save(setupTableRecord);
    }

    @Test
    void setTableStatus_SameStatus_Test() {
        Integer tableId = 1;

        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("UTC"));

        TableRecord setupTableRecord = new TableRecord();
        setupTableRecord.setId(tableId);
        setupTableRecord.updateStatus(TableStatus.RESERVED);
        setupTableRecord.setStatusUpdated(startTime);

        when(mockTableRepository.findById(tableId)).thenReturn(Optional.of(setupTableRecord));
        when(mockTableRepository.save(setupTableRecord)).thenReturn(null);

        var actualTableRecord = classUnderTest.setTableStatus(tableId, TableStatus.RESERVED);


        assertEquals(tableId, actualTableRecord.getId());
        assertEquals(TableStatus.RESERVED, actualTableRecord.getStatus());
        assertEquals(startTime, actualTableRecord.getStatusUpdated());

        verify(mockTableRepository, times(1)).findById(tableId);
        verify(mockTableRepository, times(1)).save(setupTableRecord);
    }

    @Test
    void setTableStatus_noRecordFound_Test() {
        Integer tableId = 1;
        when(mockTableRepository.findById(tableId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> classUnderTest.setTableStatus(tableId, TableStatus.RESERVED));
        verify(mockTableRepository, times(1)).findById(tableId);
    }
}
