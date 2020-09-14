package team.codex.trial.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import team.codex.trial.Application;
import team.codex.trial.TestBase;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CollectorServiceTest extends TestBase {

    @Autowired
    private CollectorService collectorService;

    @Test
    public void createAirport() {
        var airport = new AirportData("AAA",1, 2);
        collectorService.createAirport(airport);

        var storedAirport = dataContainer.findAirportData(airport.getIata());
        assertTrue(storedAirport.isPresent());
        assertEquals(airport, storedAirport.get());

        assertThrows(ResponseStatusException.class, () -> collectorService.createAirport(airport));
    }

    @Test
    public void deleteAirport() {
        collectorService.deleteAirport(iata);
        assertFalse(dataContainer.findAirportData(iata).isPresent());
        assertThrows(ResponseStatusException.class, () -> collectorService.deleteAirport(iata));
    }

    @Test
    public void addDataPoint_whenIataUnknown() {
        assertThrows(ResponseStatusException.class,
                () -> collectorService.addDataPoint("random iata", null, null));
    }

    @Test
    public void addDataPoint() {
        var dp = new DataPoint(1, 1, 1, 1, 1, DataPointType.WIND);
        collectorService.addDataPoint(iata, dp.getType(), dp);

        var airportData = dataContainer.findAirportData(iata);
        assertTrue(airportData.isPresent());
        assertEquals(iata, airportData.get().getIata());
        assertNotNull(airportData.get().getAtmosphericInformation());
        assertEquals(dp, airportData.get().getAtmosphericInformation().getContent().get(dp.getType()));
    }
}
