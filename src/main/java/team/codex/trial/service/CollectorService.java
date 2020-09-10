package team.codex.trial.service;

import team.codex.trial.exception.WeatherException;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

public interface CollectorService {

  void addDataPoint(String iata, DataPointType pointType, DataPoint dp) throws WeatherException;

  AirportData addAirport(String iata, int latitude, int longitude);

  void deleteAirport(String iata);
}
