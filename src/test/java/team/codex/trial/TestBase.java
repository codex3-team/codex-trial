package team.codex.trial;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import team.codex.trial.data.DataContainer;
import team.codex.trial.model.AirportData;

public class TestBase {

    @Autowired
    protected DataContainer dataContainer;

    protected final String iata = "BOS";

    @Before
    public void setup() {
        dataContainer.reset();
        var airport = new AirportData(iata, 1, 2);
        dataContainer.addAirport(airport);
    }
}
