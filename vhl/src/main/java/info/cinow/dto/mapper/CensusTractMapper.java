package info.cinow.dto.mapper;

import info.cinow.dto.CensusTractDto;
import info.cinow.model.CensusTract;

public class CensusTractMapper {
    public static CensusTractDto toDto(CensusTract censusTract) {
        if (censusTract == null) {
            return null;
        }

        CensusTractDto dto = new CensusTractDto();
        dto.setId(censusTract.getGid());
        dto.setTract(censusTract.getTract());
        dto.setGeometry(censusTract.getPolygon());
        return dto;
    }
}
