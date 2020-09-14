package team.codex.trial.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Objects;

// Assignment is not clear about this data structure.
// For now let's say it expects to use it only for loading CSV data
// Anyway I added couple validations here in case if we need to switch airport data format to this thing
public class ExternalAirportData implements Serializable {

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;

    @Pattern(regexp = "^(\\s*|[a-zA-Z]{3})$")
    private String iata;

    @Pattern(regexp = "^(\\s*|[a-zA-Z]{4})$")
    private String icao;

    @Digits(integer = 6, fraction = 4)
    private double latitude;

    @Digits(integer = 6, fraction = 4)
    private double longitude;

    @PositiveOrZero
    private int altitude;

    @Digits(integer = 2, fraction = 2)
    private double tzOffset;

    private DST dst;

    public ExternalAirportData() {
        iata = "";
        icao = "";
    }

    public ExternalAirportData(String city, String country, String iata, String icao, double latitude, double longitude,
                               int altitude, double tzOffset, DST dst) {
        this.city = city;
        this.country = country;
        this.iata = iata;
        this.icao = icao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.tzOffset = tzOffset;
        this.dst = dst;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getIata() {
        return iata;
    }

    public String getIcao() {
        return icao;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public double getTzOffset() {
        return tzOffset;
    }

    public DST getDst() {
        return dst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalAirportData that = (ExternalAirportData) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                altitude == that.altitude &&
                Double.compare(that.tzOffset, tzOffset) == 0 &&
                Objects.equals(city, that.city) &&
                Objects.equals(country, that.country) &&
                Objects.equals(iata, that.iata) &&
                Objects.equals(icao, that.icao) &&
                dst == that.dst;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, iata, icao, latitude, longitude, altitude, tzOffset, dst);
    }
}
