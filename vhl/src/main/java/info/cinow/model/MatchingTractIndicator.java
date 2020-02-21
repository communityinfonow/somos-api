package info.cinow.model;

/**
 * MatchingTractIndicator
 */
public interface MatchingTractIndicator {

    public MatchingCensusTractsIndicatorId getId();

    public MatchingTract getMatchingTracts();

    public Integer getRank();

    public Indicator getIndicator();
}