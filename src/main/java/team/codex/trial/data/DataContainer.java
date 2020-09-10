package team.codex.trial.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.AtmosphericInformation;

public class DataContainer {

  /**
   * all known airports
   */
  private final static List<AirportData> airportData = Collections
      .synchronizedList(new ArrayList<>());

  /**
   * Internal performance counter to better understand most requested information, this map can be
   * improved but for now provides the basis for future performance optimizations. Due to the
   * stateless deployment architecture we don't want to write this to disk, but will pull it off
   * using a REST request and aggregate with other performance metrics
   */
  private final static Map<AirportData, Integer> requestFrequency = new HashMap<>();

  private final static Map<Double, Integer> radiusFreq = new HashMap<>();

  static {
    init();
  }

  /**
   * A dummy init method that loads hard coded data
   */
  protected static void init() {
    airportData.clear();
    requestFrequency.clear();

    addAirport("BOS", 42, -71);
    addAirport("EWR", 40, -74);
    addAirport("JFK", 40, -73);
    addAirport("LGA", 40, -75);
    addAirport("MMU", 40, -76);
  }

  public static void reset() {
    init();
  }

  public static AirportData addAirport(String iataCode, int latitude, int longitude) {
    AirportData ad = new AirportData(iataCode, latitude, longitude, new AtmosphericInformation());
    airportData.add(ad);
    return ad;
  }

  public static void UpdateReqeustFreqeuncy(AirportData airportData, Double radius) {
    requestFrequency.put(airportData, requestFrequency.getOrDefault(airportData, 0) + 1);
    radiusFreq.put(radius, radiusFreq.getOrDefault(radius, 0));
  }

  public static List<AirportData> getAirportData() {
    return Collections.unmodifiableList(airportData);
  }

  public static Map<AirportData, Integer> getRequestFrequency() {
    return Collections.unmodifiableMap(requestFrequency);
  }

  public static Map<Double, Integer> getRadiusFreq() {
    return Collections.unmodifiableMap(radiusFreq);
  }
}