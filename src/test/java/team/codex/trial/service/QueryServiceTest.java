package team.codex.trial.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import team.codex.trial.Application;
import team.codex.trial.TestBase;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QueryServiceTest extends TestBase {

    @Inject
    private QueryService queryService;

    @Inject
    private CollectorService collectorService;

    @Test
    public void findAirportData_whenIataUnknown() {
        assertThrows(ResponseStatusException.class,
                () -> queryService.findAirportData("random iata"));
    }

    @Test
    public void queryWeather() {
        var dp1 = new DataPoint(1, 1, 1, 1, 1, DataPointType.WIND);
        collectorService.addDataPoint(iata, dp1.getType(), dp1);

        var weather = queryService.queryWeather(iata, 1);
        assertNotNull(weather);
        assertEquals(1, weather.size());
        assertEquals(1, weather.get(0).getContent().size());
        assertEquals(dp1, weather.get(0).getContent().get(dp1.getType()));

        var dp2 = new DataPoint(1, 1, 1, 1, 1, DataPointType.HUMIDITY);
        collectorService.addDataPoint(iata, dp2.getType(), dp2);

        weather = queryService.queryWeather(iata, 1);
        assertNotNull(weather);
        assertEquals(1, weather.size());
        assertEquals(2, weather.get(0).getContent().size());
        assertEquals(dp1, weather.get(0).getContent().get(dp1.getType()));
        assertEquals(dp2, weather.get(0).getContent().get(dp2.getType()));
    }

    @Test
    public void queryPingResponse() {
        var airportData = dataContainer.getAirportData();

        var response = queryService.queryPingResponse();
        assertEquals(airportData.size(), response.getDataSize());
        assertEquals(airportData.size(), response.getIataFrequency().size());

        queryService.queryWeather(iata, 1);
        response = queryService.queryPingResponse();
        assertEquals(airportData.size(), response.getDataSize());
        assertTrue(response.getIataFrequency().get(iata) > 0);
    }

    @Test
    public void findAirportData() {
        var airportData = queryService.findAirportData(iata);
        assertEquals(iata, airportData.getIata());
        assertThrows(ResponseStatusException.class, () -> queryService.findAirportData("random iata"));
    }
}
