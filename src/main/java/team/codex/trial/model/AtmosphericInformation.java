package team.codex.trial.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * encapsulates sensor information for a particular location
 */
public class AtmosphericInformation implements Serializable {

    private Map<DataPointType, DataPoint> content = new HashMap<>();

    // Last time this data was updated, in milliseconds since UTC epoch
    private long lastUpdateTime;

    public void updateContent(DataPointType pointType, DataPoint dataPoint) {
        lastUpdateTime = System.currentTimeMillis();
        content.put(pointType, dataPoint);
    }

    public boolean isFresh() {
        // What is expected to do here?
        // Condition below is always true. Usually such checks are preformed with certain threshold like
        // Instant.now().getEpochSecond() - lastUpdateTime <= SOME_THRESHOLD
        // Changing logic here may result in providing unexpected data to client.
        // Such changes can be considered as contract change and it needs to be discussed within a team.
        return lastUpdateTime < Instant.now().getEpochSecond();
    }

    @JsonProperty("Contents")
    public Map<DataPointType, DataPoint> getContent() {
        return content;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtmosphericInformation that = (AtmosphericInformation) o;
        return lastUpdateTime == that.lastUpdateTime &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, lastUpdateTime);
    }
}