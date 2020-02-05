package info.cinow.dto.mapper;

import org.springframework.stereotype.Component;

import info.cinow.dto.MatchedCensusTractDto;
import info.cinow.model.CensusTract;
import info.cinow.model.MatchingTract;

/**
 * CensusTractMapperImpl
 */
@Component("matchedCensusTractMapper")
public class MatchedCensusTractMapperImpl implements MatchedCensusTractMapper {

    @Override
    public MatchedCensusTractDto toDto(MatchingTract censusTract) {
        if (censusTract == null) {
            return null;
        }
        MatchedCensusTractDto dto = new MatchedCensusTractDto();
        CensusTract childTract = censusTract.getChildTract();
        dto.setId(childTract.getGid());
        dto.setRank(censusTract.getRank());
        dto.setLifeExpectancyDifference(censusTract.getLifeExpentancyDifference());
        return dto;
    }
}