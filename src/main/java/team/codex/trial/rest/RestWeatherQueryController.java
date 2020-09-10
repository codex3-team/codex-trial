package team.codex.trial.rest;

import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.codex.trial.model.AtmosphericInformation;
import team.codex.trial.service.QueryService;

/**
 * The Weather App REST endpoint allows clients to query, update and check health stats. Currently,
 * all data is held in memory. The end point deploys to a single container
 *
 * @author code test administrator
 */
@RestController
@RequestMapping("/query")
public class RestWeatherQueryController {

  @Autowired
  private QueryService queryService;

  /**
   * Retrieve service health including total size of valid data points and request frequency
   * information.
   *
   * @return health stats for the service as a string
   */
  @GetMapping("/ping")
  public Map<String, Object> ping() {
    return queryService.queryPingResponse();
  }

  /**
   * Given a query in json format {'iata': CODE, 'radius': km} extracts the requested airport
   * information and return a list of matching atmosphere information.
   *
   * @param iata         the iataCode
   * @param radius the radius in km
   * @return a list of atmospheric information
   */
  @GetMapping("/weather/{iata}/{radius}")
  public Response weather(@PathVariable String iata, @PathVariable String radius) {
    double radiusValue =
        radius == null || radius.trim().isEmpty() ? 0 : Double.parseDouble(radius);

    if (queryService.findAirportData(iata) == null) {
      return Response.status(404).build();
    }

    List<AtmosphericInformation> informationList = queryService.queryWeather(iata, radiusValue);
    return Response.ok(informationList).build();
  }
}
