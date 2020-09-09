package team.codex.trial.service;

import team.codex.trial.exception.WeatherException;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

import java.util.Set;

public interface CollectorService {
    void addDataPoint(String iataCode, DataPointType pointType, DataPoint dp) throws WeatherException;

    Set<String> getAirports();

}
