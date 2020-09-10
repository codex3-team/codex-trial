package team.codex.trial.model;

/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData {

  /**
   * the three letter IATA code
   */
  private String iata;

  /**
   * latitude value in degrees
   */
  private int latitude;

  /**
   * longitude value in degrees
   */
  private int longitude;

  private AtmosphericInformation atmosphericInformation;

  public AirportData(String iata, int latitude, int longitude,
      AtmosphericInformation atmosphericInformation) {
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
