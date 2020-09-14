package team.codex.trial.service;

import org.springframework.stereotype.Service;
import team.codex.trial.data.DataContainer;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CollectorService {

    @Inject
    private QueryService queryService;

    @Inject
    private DataContainer dataContainer;

    /**
     * Update the airports weather data with the collected data.
     *
     * @param iataCode  the 3 letter IATA code
     * @param pointType the point type {@link DataPointType}
     * @param dp        a datapoint object holding pointType data
     */
    public void addDataPoint(String iataCode, DataPointType pointType, DataPoint dp) {
        var airportData = queryService.findAirportData(iataCode);
        airportData.getAtmosphericInformation().updateContent(pointType, dp);
        dataContainer.updateAirportData(airportData);
    }

    public Set<String> getAirports() {
        return dataContainer.getAirportData().stream().map(a -> a.getIata()).collect(Collectors.toSet());
    }
}