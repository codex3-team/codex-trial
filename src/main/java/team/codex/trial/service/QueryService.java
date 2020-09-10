package team.codex.trial.service;

import java.util.List;
import java.util.Map;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.AtmosphericInformation;

public interface QueryService {

  /**
   * Given an iataCode find the airport data
   *
   * @param iataCode as a string
   * @return airport data or null if not found
   */
  AirportData findAirportData(String iataCode);

  List<AtmosphericInformation> queryWeather(String iata, double radius);

  Map<String, Object> queryPingResponse();
}
