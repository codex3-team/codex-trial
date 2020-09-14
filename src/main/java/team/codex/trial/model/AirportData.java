package team.codex.trial.model;

import java.io.Serializable;

/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData implements Serializable {

    // Three letter IATA code
    private String iata;

    // Latitude value in degrees
    private int latitude;

    // Longitude value in degrees
    private int longitude;

    private AtmosphericInformation atmosphericInformation;

    public AirportData(String iata, int latitude, int longitude, AtmosphericInformation atmosphericInformation) {
        this.iata = iata;
        this.latitude = latitude;
        this.longitude = longitude;
        this.atmosphericInformation = atmosphericInformation;
    }

    public String getIata() {
        return iata;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public AtmosphericInformation getAtmosphericInformation() {
        return atmosphericInformation;
    }
}
