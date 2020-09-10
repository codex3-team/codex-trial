package team.codex.trial.service;

import java.util.Set;
import team.codex.trial.exception.WeatherException;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

public interface CollectorService {

  void addDataPoint(String iataCode, DataPointType pointType, DataPoint dp) throws WeatherException;

  Set<String> getAirports();
}
