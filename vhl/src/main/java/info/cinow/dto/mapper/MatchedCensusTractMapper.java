package info.cinow.dto.mapper;

import info.cinow.dto.MatchedCensusTractDto;
import info.cinow.model.MatchingTract;

public interface MatchedCensusTractMapper {
    public MatchedCensusTractDto toDto(MatchingTract matchingTract);
}
