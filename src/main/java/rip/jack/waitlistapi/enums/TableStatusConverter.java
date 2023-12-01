package rip.jack.waitlistapi.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TableStatusConverter implements AttributeConverter<TableStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TableStatus tableStatus) {
        if (tableStatus == null) {
            return null;
        }

        return tableStatus.getId();
    }

    @Override
    public TableStatus convertToEntityAttribute(Integer dbData) {
        return TableStatus.valueOf(dbData);
    }
}
