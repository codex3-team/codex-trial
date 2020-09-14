package team.codex.trial.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.codex.trial.model.AtmosphericInformation;
import team.codex.trial.model.QueryPingResponse;
import team.codex.trial.service.QueryService;

import javax.inject.Inject;
import java.util.List;

/**
 * The Weather App REST endpoint allows clients to query, update and check health stats. Currently, all data is
 * held in memory. The end point deploys to a single container
 *
 * @author code test administrator
 */
@RestController
@RequestMapping("/query")
public class RestWeatherQueryController {

    @Inject
    private QueryService queryService;

    /**
     * Retrieve service health including total size of valid data points and request frequency information.
     *
     * @return health stats for the service as a string
     */
    @GetMapping("/ping")
    public ResponseEntity<QueryPingResponse> ping() {
        return ResponseEntity.ok(queryService.queryPingResponse());
    }

    /**
     * Given a query in json format {'iata': CODE, 'radius': km} extracts the requested airport information and
     * return a list of matching atmosphere information.
     *
     * @param iata   the iataCode
     * @param radius the radius in km
     * @return a list of atmospheric information
     */
    @GetMapping("/weather/{iata}/{radius}")
    public ResponseEntity<List<AtmosphericInformation>> weather(@PathVariable String iata, @PathVariable double radius) {
        if (queryService.findAirportData(iata) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(queryService.queryWeather(iata, radius));
    }
}