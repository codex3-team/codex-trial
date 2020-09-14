package team.codex.trial.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import team.codex.trial.Application;
import team.codex.trial.data.DataContainer;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CollectorServiceTest {
    @Inject
    private DataContainer dataContainer;

    @Inject
    private CollectorService collectorService;

    @Before
    public void setup() {
        dataContainer.reset();
    }

    @Test
    public void addDataPoint_whenIataUnknown() {
        assertThrows(ResponseStatusException.class,
                () -> collectorService.addDataPoint("random iata", null, null));
    }

    @Test
    public void addDataPoint() {
        final String iata = "BOS";
        var dp = new DataPoint(1, 1, 1, 1, 1, DataPointType.WIND);
        collectorService.addDataPoint(iata, dp.getType(), dp);

        var airportData = dataContainer.findAirportData(iata);
        assertTrue(airportData.isPresent());
        assertEquals(iata, airportData.get().getIata());
        assertNotNull(airportData.get().getAtmosphericInformation());
        assertEquals(dp, airportData.get().getAtmosphericInformation().getContent().get(dp.getType()));
    }
}
