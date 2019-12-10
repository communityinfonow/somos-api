package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

/**
 * CensusTractData
 */
@Entity
@Data
public class CensusTractData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // using IDENTITY here since I need to insert data without ids
                                                        // manually to the database
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "data")
    private CensusTract censusTract;

    @Column(name = "life_expectancy")
    private Double lifeExpectancy;

    @Column(name = "percent_census_mail_returns2010")
    private Double percentCensusMailReturns2010;

    @Column(name = "incarceration_rate")
    private Double incarcerationRate;

    @Column(name = "cancer_risk_per_million")
    private Double cancerRiskPerMillion;

    @Column(name = "respiratory_hazard_quotient")
    private Double respiratoryHazardQuotient;

    @Column(name = "percent_binge_drinking")
    private Double percentBingeDrinking;

    @Column(name = "percent_high_colesterol")
    private Double percentHighColesterol;

    @Column(name = "percent_smoking")
    private Double percentSmoking;

    @Column(name = "percent_visit_doctor_last_year")
    private Double percentVisitDoctorLastYear;

    @Column(name = "percent_adult_obesity")
    private Double percentAdultObesity;

    @Column(name = "average_travel_miles_per_person")
    private Double averageTravelMilesPerPerson;

    @Column(name = "total_jobsper1000")
    private Double totalJobsper10000;

    @Column(name = "transportation_cost_index")
    private Double transportationCostIndex;

}