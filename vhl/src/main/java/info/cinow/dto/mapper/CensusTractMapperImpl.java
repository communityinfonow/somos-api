package info.cinow.dto.mapper;

import org.springframework.stereotype.Component;

import info.cinow.dto.CensusTractDto;
import info.cinow.model.CensusTract;

/**
 * CensusTractMapperImpl
 */
@Component("censusTractMapper")
public class CensusTractMapperImpl implements CensusTractMapper {

    @Override
    public CensusTractDto toDto(CensusTract censusTract) {
        if (censusTract == null) {
            return null;
        }
        CensusTractDto dto = new CensusTractDto();
        dto.setId(censusTract.getGid());
        dto.setTract(censusTract.getTract());
        dto.setGeometry(censusTract.getPolygon());
        // dto.setPhotos(censusTract.getPhotos());
        return dto;
    }
}