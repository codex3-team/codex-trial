package team.codex.trial.service;

import static team.codex.trial.util.CalculationUtils.calculateDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import team.codex.trial.data.DataContainer;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.AtmosphericInformation;

@Service
public class QueryServiceImpl implements QueryService {

  @Override
  public Set<String> getAirports() {
    return DataContainer.getAirportData().stream().map(AirportData::getIata)
        .collect(Collectors.toSet());
  }

  @Override
  public AirportData findAirportData(String iata) {
    return DataContainer.getAirportData().stream()
        .filter(ap -> ap.getIata().equals(iata))
        .findFirst().orElse(null);
  }

  @Override
  public List<AtmosphericInformation> queryWeather(String iata, double radius) {
    updateRequestFrequency(iata, radius);
    AirportData airportData = findAirportData(iata);
    List<AtmosphericInformation> retval;

    if (radius == 0) {
      retval = Collections.singletonList(airportData.getAtmosphericInformation());
    } else {
      List<AtmosphericInformation> list = new ArrayList<>();

      for (AirportData data : DataContainer.getAirportData()) {
        if (calculateDistance(airportData, data) <= radius) {
          list.add(data.getAtmosphericInformation());
        }
      }

      retval = list;
    }
    return retval;
  }

  @Override
  public Map<String, Object> queryPingResponse() {
    Map<String, Object> retval = new HashMap<>();
    long datasize = 0L;

    for (AirportData a : DataContainer.getAirportData()) {
      AtmosphericInformation info = a.getAtmosphericInformation();

      if (info.isFresh()) {
        datasize++;
      }
    }

    retval.put("datasize", datasize);
    Map<String, Double> f = new HashMap<>();

    // fraction of queries
    for (AirportData data : DataContainer.getAirportData()) {
      Map<AirportData, Integer> qm = DataContainer.getRequestFrequency();
      double fr = (double) qm.getOrDefault(data, 0) / qm.size();
      f.put(data.getIata(), fr);
    }

    retval.put("iata_freq", f);
    boolean seen = false;
    Double best = null;

    for (Double aDouble : DataContainer.getRadiusFreq().keySet()) {
      if (!seen || Double.compare(aDouble, best) > 0) {
        seen = true;
        best = aDouble;
      }
    }

    int m = (seen ? best.intValue() : 1000) + 1;

    int[] h = new int[m];

    for (Map.Entry<Double, Integer> e : DataContainer.getRadiusFreq().entrySet()) {
      int i = e.getKey().intValue() % 10;
      h[i] += e.getValue();
    }

    retval.put("radius_freq", h);
    return retval;
  }

  /**
   * Records information about how often requests are made
   *
   * @param iata   an iata code
   * @param radius query radius
   */
  private void updateRequestFrequency(String iata, Double radius) {
    AirportData airportData = findAirportData(iata);
    DataContainer.updateRequestFrequency(airportData, radius);
  }
}
