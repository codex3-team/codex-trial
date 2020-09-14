package team.codex.trial.data;

import org.junit.Test;
import org.springframework.web.server.ResponseStatusException;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

import static org.junit.Assert.*;

public class DataContainerTest {

    private static final String iata = "BOS";

    @Test
    public void findAirportData() {
        var dataContainer = new DataContainer();
        var airport = dataContainer.findAirportData(iata);
        assertTrue(airport.isPresent());
        assertEquals(iata, airport.get().getIata());
        assertFalse(dataContainer.findAirportData("random iata").isPresent());
    }

    @Test
    public void updateAirportData() {
        var dataContainer = new DataContainer();

        var airport = dataContainer.findAirportData(iata);
        assertTrue(airport.isPresent());
        assertTrue(airport.get().getAtmosphericInformation().getContent().isEmpty());

        var dp = new DataPoint(1, 1, 1, 1, 1, DataPointType.WIND);
        airport.get().getAtmosphericInformation().updateContent(dp.getType(), dp);

        var airport2 = dataContainer.findAirportData(iata);
        assertTrue(airport2.isPresent());
        assertTrue(airport.get() != airport2.get());
        assertTrue(airport2.get().getAtmosphericInformation().getContent().isEmpty());

        dataContainer.updateAirportData(airport.get());
        var updatedAirport = dataContainer.findAirportData(iata);
        assertTrue(updatedAirport.isPresent());
        assertTrue(airport.get() != updatedAirport.get());
        assertEquals(dp, updatedAirport.get().getAtmosphericInformation().getContent().get(dp.getType()));
    }

    @Test
    public void addAirport() {
        var dataContainer = new DataContainer();
        final String newIata = "TEST";
        assertFalse(dataContainer.findAirportData(newIata).isPresent());

        dataContainer.addAirport(newIata, 1, 2);

        var airport = dataContainer.findAirportData(newIata);
        assertTrue(airport.isPresent());
        assertEquals(1, airport.get().getLatitude());
        assertEquals(2, airport.get().getLongitude());
        assertTrue(airport.get().getAtmosphericInformation().getContent().isEmpty());
    }

    @Test
    public void updateRequestFrequency() {
        var dataContainer = new DataContainer();

        var airport = dataContainer.findAirportData(iata);
        assertTrue(airport.isPresent());
        dataContainer.updateRequestFrequency(airport.get(), 1d);

        var requestFrequency = dataContainer.getRequestFrequencyMap();
        assertNotNull(requestFrequency);
        assertEquals(1, requestFrequency.get(iata).intValue());
    }
}
