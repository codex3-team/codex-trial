package team.codex.trial.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;
import team.codex.trial.service.CollectorService;
import team.codex.trial.service.QueryService;

import javax.inject.Inject;
import java.util.Set;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport weather collection
 * sites via secure VPN.
 *
 * @author code test administrator
 */
@RestController
@RequestMapping("/collect")
public class RestWeatherCollectorController {

    @Inject
    private CollectorService collectorService;

    @Inject
    private QueryService queryService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("ready");
    }

    @PostMapping("/weather/{iata}/{pointType}")
    public void updateWeather(@PathVariable String iataCode, @PathVariable DataPointType dataPointType, @RequestBody DataPoint dataPoint) {
        collectorService.addDataPoint(iataCode, dataPointType, dataPoint);
    }

    @GetMapping("/airports")
    public ResponseEntity<Set<String>> getAirports() {
        return ResponseEntity.ok(collectorService.getAirports());
    }

    @GetMapping("/airport/{iata}")
    public ResponseEntity<AirportData> getAirport(@PathVariable String iata) {
        var airportData = queryService.findAirportData(iata);
        if (airportData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airportData);
    }

    // NOTE: it would be nice to refactor endpoints to resource oriented design
    // but assignment forces to keep current API contract
    @PostMapping("/airport/{iata}/{latitude}/{longitude}")
    public void createAirport(@PathVariable String iata, @PathVariable int latitude, @PathVariable int longitude) {
        collectorService.createAirport(new AirportData(iata, latitude, longitude));
    }

    @DeleteMapping("/airport/{iata}")
    public void deleteAirport(@PathVariable String iata) {
        collectorService.deleteAirport(iata);
    }

    @GetMapping("/exit")
    public void exit() {
        System.exit(0);
    }
}