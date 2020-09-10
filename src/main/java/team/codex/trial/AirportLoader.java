package team.codex.trial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

/**
 * An utility class used to read airports.dat file, and send airport data to AWS
 */
public class AirportLoader {

  private static final String INPUT_FILENAME = "airports.dat";
  private static final String BASE_URI = "http://localhost:8080";

  /**
   * end point to supply updates
   */
  private WebTarget collect;

  public AirportLoader() {
    Client client = ClientBuilder.newClient();
    collect = client.target(BASE_URI + "/collect");
  }

  public static void main(String[] args) {
    AirportLoader wc = new AirportLoader();
    wc.pingCollect();
    wc.importAirports();
    System.out.print("complete");
    System.exit(0);
  }

  public void pingCollect() {
    WebTarget path = collect.path("/ping");
    Response response = path.request().get();
    System.out.print("collect.ping: " + response.readEntity(String.class) + "\n");
  }

  public void addAirport(String iata, int lat, int lng) {
    Response response = collect.path("/airport/" + iata + "/" + lat + "/" + lng).request().post(null);
    System.out.println("addAirport: " + response.readEntity(String.class));
  }

  public void importAirports() {
    try (Stream<String> lines = Files.lines(Paths.get(INPUT_FILENAME))) {
      lines.skip(1).map(s -> s.split(","))
          .filter(strings -> strings.length == 9)
          .forEach(strings -> {
            String iata = strings[2].replaceAll("\"", "");
            int latitude = Integer.parseInt(strings[4]);
            int longitude = Integer.parseInt(strings[5]);
            addAirport(iata, latitude, longitude);
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
