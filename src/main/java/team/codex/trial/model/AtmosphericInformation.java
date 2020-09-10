package team.codex.trial.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import team.codex.trial.exception.WeatherException;

/**
 * encapsulates sensor information for a particular location
 */
public class AtmosphericInformation {

  Map<DataPointType, DataPoint> Contents = new HashMap<>();

  /**
   * the last time this data was updated, in milliseconds since UTC epoch
   */
  private long lastUpdateTime;

  public AtmosphericInformation() {

  }

  public void updateContents(DataPointType pointType, DataPoint dataPoint) throws WeatherException {
    lastUpdateTime = System.currentTimeMillis();
    Contents.put(pointType, dataPoint);
  }

  public boolean isFresh() {
    return lastUpdateTime < Instant.now().getEpochSecond();
  }

}