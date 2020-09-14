package team.codex.trial;

import org.junit.Before;
import team.codex.trial.data.DataContainer;
import team.codex.trial.model.AirportData;

import javax.inject.Inject;

public class TestBase {

    @Inject
    protected DataContainer dataContainer;

    protected final String iata = "BOS";

    @Before
    public void setup() {
        dataContainer.reset();
        var airport = new AirportData(iata, 1, 2);
        dataContainer.addAirport(airport);
    }
}
