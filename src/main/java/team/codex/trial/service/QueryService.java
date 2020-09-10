package team.codex.trial.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.AtmosphericInformation;

public interface QueryService {

  Set<String> getAirports();

  /**
   * Given an iata find the airport data
   *
   * @param iata as a string
   * @return airport data or null if not found
   */
  AirportData findAirportData(String iata);

  List<AtmosphericInformation> queryWeather(String iata, double radius);

  Map<String, Object> queryPingResponse();
}
