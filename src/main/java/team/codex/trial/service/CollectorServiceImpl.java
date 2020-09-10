package team.codex.trial.service;

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
   * @param iata      the 3 letter IATA code
   * @param pointType the point type {@link DataPointType}
   * @param dp        a datapoint object holding pointType data
   * @throws WeatherException if the update can not be completed
   */
  @Override
  public void addDataPoint(String iata, DataPointType pointType, DataPoint dp)
      throws WeatherException {
    AirportData airportData = queryService.findAirportData(iata);

    if (airportData == null) {
      throw new WeatherException("airport not found");
    }

    airportData.getAtmosphericInformation().updateContents(pointType, dp);
  }

  @Override
  public AirportData addAirport(String iata, int latitude, int longitude) {
    AirportData data = queryService.findAirportData(iata);

    if (data == null) {
      data = DataContainer.addAirport(iata, latitude, longitude);
    }

    return data;
  }

  @Override
  public void deleteAirport(String iata) {
    DataContainer.deleteAirport(iata);
  }
}