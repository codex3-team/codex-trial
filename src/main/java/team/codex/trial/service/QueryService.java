package team.codex.trial.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import team.codex.trial.data.DataContainer;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.AtmosphericInformation;
import team.codex.trial.model.QueryPingResponse;
import team.codex.trial.util.DistanceUtil;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QueryService {

    @Inject
    private DataContainer dataContainer;

    /**
     * Given an iataCode find the airport data
     *
     * @param iataCode as a string
     * @return airport data or null if not found
     */
    public AirportData findAirportData(String iataCode) {
        var airportData = dataContainer.findAirportData(iataCode);
        if (airportData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return airportData.get();
    }

    public List<AtmosphericInformation> queryWeather(String iata, double radius) {
        updateRequestFrequency(iata, radius);

        var airportData = findAirportData(iata);
        if (radius == 0) {
            return Collections.singletonList(airportData.getAtmosphericInformation());
        }
        return dataContainer.getAirportData().stream().filter(data -> DistanceUtil.calculateDistance(airportData, data) <= radius)
                .map(data -> data.getAtmosphericInformation()).collect(Collectors.toList());
    }

    public QueryPingResponse queryPingResponse() {
        var airportData = dataContainer.getAirportData();
        long dataSize = airportData.stream().filter(ad -> ad.getAtmosphericInformation().isFresh()).count();

        Map<String, Double> f = new HashMap<>();

        var qm = dataContainer.getRequestFrequencyMap();
        if (dataSize > 0) {
            f = airportData.stream().map(ad -> ad.getIata()).collect(
                    Collectors.toMap(iata -> iata, iata -> qm.getOrDefault(iata, 0) / (dataSize * 1.0d)));
        }

        var radiusFrequencyMap = dataContainer.getRadiusFrequencyMap();
        Double best = radiusFrequencyMap.keySet().stream().mapToDouble(r -> r).max().orElse(1000);

        // It's better not to touch working legacy
        int m = best.intValue() + 1;

        int[] h = new int[m];
        for (Map.Entry<Double, Integer> e : radiusFrequencyMap.entrySet()) {
            int i = e.getKey().intValue() % 10;
            h[i] += e.getValue();
        }

        return new QueryPingResponse(dataSize, f, Arrays.stream(h).boxed().collect(Collectors.toList()));
    }

    /**
     * Records information about how often requests are made
     *
     * @param iata   an iata code
     * @param radius query radius
     */
    private void updateRequestFrequency(String iata, Double radius) {
        dataContainer.updateRequestFrequency(findAirportData(iata), radius);
    }
}
