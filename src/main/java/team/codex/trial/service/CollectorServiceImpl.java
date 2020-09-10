package team.codex.trial.service;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.codex.trial.data.DataContainer;
import team.codex.trial.exception.WeatherException;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

@Service
public class CollectorServiceImpl implements CollectorService {

  @Autowired
  private QueryService queryService;

  /**
   * Update the airports weather data with the collected data.
   *
   * @param iataCode  the 3 letter IATA code
   * @param pointType the point type {@link DataPointType}
   * @param dp        a datapoint object holding pointType data
   * @throws WeatherException if the update can not be completed
   */
  @Override
  public void addDataPoint(String iataCode, DataPointType pointType, DataPoint dp)
      throws WeatherException {
    AirportData airportData = queryService.findAirportData(iataCode);

    if (airportData != null) {
      airportData.atmosphericInformation.updateContents(pointType, dp);
    }
  }

  @Override
  public Set<String> getAirports() {
    return DataContainer.getAirportData().stream().map(a -> a.iata).collect(Collectors.toSet());
  }
}