package team.codex.trial.model;

import java.io.Serializable;
import java.util.Objects;

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

    private AtmosphericInformation atmosphericInformation = new AtmosphericInformation();

    public AirportData(String iata, int latitude, int longitude) {
        this.iata = iata;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirportData that = (AirportData) o;
        return latitude == that.latitude &&
                longitude == that.longitude &&
                Objects.equals(iata, that.iata) &&
                Objects.equals(atmosphericInformation, that.atmosphericInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iata, latitude, longitude, atmosphericInformation);
    }
}
