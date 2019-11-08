package info.cinow.dto.mapper;

import info.cinow.dto.CensusTractDto;
import info.cinow.model.CensusTract;

public interface CensusTractMapper {
    public CensusTractDto toDto(CensusTract censusTract);
}
