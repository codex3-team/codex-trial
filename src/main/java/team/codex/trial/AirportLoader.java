package team.codex.trial;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Scanner;

public class AirportLoader {
    private static final int IATA_INDEX = 2;
    private static final int LATITUDE_INDEX = 4;
    private static final int LONGITUDE_INDEX = 5;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.print("Missing file to upload");
            System.exit(1);
        }

        // We also can add timeouts for production usage
        // but for simplicity we keep it straight
        var restTemplate = new RestTemplate();

        try (var scanner = new Scanner(new File(args[0]))) {
            while (scanner.hasNextLine()) {
                var row = scanner.nextLine().split(",");

                var path = String.format("http://localhost:8080/collect/airport/%s/%s/%s",
                        row[IATA_INDEX], row[LATITUDE_INDEX], row[LONGITUDE_INDEX]);
                var response = restTemplate.postForEntity(path, null, Void.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    System.out.print("Error while uploading data. Status code: " + response.getStatusCode());
                    System.exit(1);
                }
            }
        }
        catch (Exception ex) {
            System.err.print("Can't upload csv: " + ex);
        }

        System.out.print("Args:" + StringUtils.join(args));
        System.exit(0);
    }
}
