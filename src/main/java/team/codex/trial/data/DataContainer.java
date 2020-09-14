package team.codex.trial.data;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;
import team.codex.trial.model.AirportData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * There are several ways to deal with storage like this.
 * What will be shown here is a simplest way to fix concurrency issues.
 * <p>
 * Alternative solution could be major interface change to provide following capabilities:
 * 1) implement thread-safe repository-like CRUD operations
 * 2) get-methods should return copy of internally stored object to avoid unexpected modifications
 * 3) copying large airport storage probably isn't a good idea so we need to provide
 * capabilities to iterate over internal storage with specified predicate.
 * <p>
 * In general such changes would lead to storage abstraction which could be easily replaced by database in future.
 */
@Component
public class DataContainer {
    /**
     * all known airports
     */
    private final Map<String, AirportData> airportDataMap = new ConcurrentHashMap<>();

    /**
     * Internal performance counter to better understand most requested information, this map can be improved but
     * for now provides the basis for future performance optimizations. Due to the stateless deployment architecture
     * we don't want to write this to disk, but will pull it off using a REST request and aggregate with other
     * performance metrics
     */
    private final Map<String, Integer> requestFrequencyMap = new ConcurrentHashMap<>();

    private final Map<Double, Integer> radiusFrequencyMap = new ConcurrentHashMap<>();

    public DataContainer() {
        init();
    }

    public void reset() {
        init();
    }

    public void addAirport(AirportData airportData) {
        airportDataMap.put(airportData.getIata(), airportData);
    }

    public boolean deleteAirport(String iata) {
        return airportDataMap.remove(iata) != null;
    }

    public Optional<AirportData> findAirportData(String iata) {
        var result = airportDataMap.get(iata);
        if (result == null) {
            return Optional.empty();
        }
        return Optional.of(SerializationUtils.clone(result));
    }

    public void updateAirportData(AirportData airportData) {
        airportDataMap.put(airportData.getIata(), airportData);
    }

    public void updateRequestFrequency(AirportData airportData, Double radius) {
        requestFrequencyMap.compute(airportData.getIata(), (k, v) -> v == null ? 1 : v + 1);
        radiusFrequencyMap.compute(radius, (k, v) -> v == null ? 1 : v + 1);
    }

    public List<AirportData> getAirportData() {
        return new ArrayList<>(airportDataMap.values());
    }

    public Map<String, Integer> getRequestFrequencyMap() {
        return Collections.unmodifiableMap(requestFrequencyMap);
    }

    public Map<Double, Integer> getRadiusFrequencyMap() {
        return Collections.unmodifiableMap(radiusFrequencyMap);
    }

    private void addAirport(String iata, int latitude, int longitude) {
        airportDataMap.put(iata, new AirportData(iata, latitude, longitude));
    }

    private void init() {
        airportDataMap.clear();
        requestFrequencyMap.clear();
        radiusFrequencyMap.clear();

        addAirport("BOS", 42, -71);
        addAirport("EWR", 40, -74);
        addAirport("JFK", 40, -73);
        addAirport("LGA", 40, -75);
        addAirport("MMU", 40, -76);
    }
}