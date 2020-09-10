package team.codex.trial.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * encapsulates sensor information for a particular location
 */
public class AtmosphericInformation {

  public static final long VALUE_LIFETIME_MILLISECONDS = 1000 * 60 * 60;

  private Map<DataPointType, DataPoint> contents = new HashMap<>();

  /**
   * the last time this data was updated, in milliseconds since UTC epoch
   */
  private Instant lastUpdateTime = Instant.now();

  public AtmosphericInformation() {
  }

  public void updateContents(DataPointType pointType, DataPoint dataPoint) {
    lastUpdateTime = Instant.now();
    contents.put(pointType, dataPoint);
  }

  public boolean isFresh() {
    return (Instant.now().toEpochMilli() - lastUpdateTime.toEpochMilli())
        < VALUE_LIFETIME_MILLISECONDS;
  }

  public Map<DataPointType, DataPoint> getContents() {
    return contents;
  }
}