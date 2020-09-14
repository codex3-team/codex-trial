package team.codex.trial.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class QueryPingResponse {
    private long dataSize;
    private Map<String, Double> iataFrequency;
    private List<Integer> radiusFrequency;

    public QueryPingResponse(long dataSize, Map<String, Double> iataFrequency, List<Integer> radiusFrequency) {
        this.dataSize = dataSize;
        this.iataFrequency = iataFrequency;
        this.radiusFrequency = radiusFrequency;
    }

    @JsonProperty("datasize")
    public long getDataSize() {
        return dataSize;
    }

    @JsonProperty("iata_freq")
    public Map<String, Double> getIataFrequency() {
        return iataFrequency;
    }

    @JsonProperty("radius_freq")
    public List<Integer> getRadiusFrequency() {
        return radiusFrequency;
    }
}
