package team.codex.trial.rest;

import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import team.codex.trial.exception.WeatherException;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;
import team.codex.trial.service.CollectorService;
import team.codex.trial.service.QueryService;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport weather collection
 * sites via secure VPN.
 *
 * @author code test administrator
 */
@RestController
@RequestMapping("/collect")
public class RestWeatherCollectorController {

  @Autowired
  private CollectorService collectorService;

  @Autowired
  private QueryService queryService;

  @RequestMapping(value = "/ping", method = RequestMethod.GET)
  public Response ping() {
    return Response.ok("ready").build();
  }

  @PostMapping("/weather/{iata}/{pointType}")
  public Response updateWeather(
      @PathVariable String iata,
      @PathVariable DataPointType pointType,
      @RequestBody DataPoint datapoint) {
    try {
      collectorService.addDataPoint(iata, pointType, datapoint);
      return Response.ok().build();
    } catch (WeatherException e) {
      return Response.status(422).build();
    } catch (Exception e) {
      return Response.serverError().build();
    }
  }

  @GetMapping("/airports")
  public Response getAirports() {
    return Response.ok(queryService.getAirports()).build();
  }

  @GetMapping("/airport/{iata}")
  public Response getAirport(@PathVariable String iata) {
    AirportData airportData = queryService.findAirportData(iata);

    if (airportData == null) {
      return Response.status(404).build();
    }

    return Response.ok(airportData).build();
  }

  @PostMapping("/airport/{iata}/{lat}/{long}")
  public Response addAirport(
      @PathVariable String iata,
      @PathVariable int lat,
      @PathVariable(name = "long") int lng) {
    return Response.ok(collectorService.addAirport(iata, lat, lng)).build();
  }

  @DeleteMapping("/airport/{iata}")
  public Response deleteAirport(@PathVariable String iata) {
    collectorService.deleteAirport(iata);
    return Response.ok().build();
  }
}
