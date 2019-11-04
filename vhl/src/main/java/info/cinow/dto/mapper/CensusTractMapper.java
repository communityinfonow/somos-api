package info.cinow.dto.mapper;

import info.cinow.dto.CensusTractDto;
import info.cinow.model.CensusTract;

public class CensusTractMapper {
    public static CensusTractDto toDto(CensusTract censusTract) {
        CensusTractDto dto = new CensusTractDto();
        dto.setId(censusTract.getGid());
        dto.setTract(censusTract.getTract());
        return dto;
    }
}
