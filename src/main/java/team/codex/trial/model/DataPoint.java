package team.codex.trial.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A collected point, including some information about the range of collected values
 *
 * @author code test administrator
 */
public class DataPoint implements Serializable {

    private double mean;
    private double first;
    private double median;
    private double last;
    private int count;
    private DataPointType type;

    public DataPoint(double mean, double first, double median, double last, int count, DataPointType type) {
        this.mean = mean;
        this.first = first;
        this.median = median;
        this.last = last;
        this.count = count;
        this.type = type;
    }

    // Mean of the observations
    public double getMean() {
        return mean;
    }

    // 1st quartile -- useful as a lower bound
    public double getFirst() {
        return first;
    }

    // 2nd quartile -- median value
    public double getMedian() {
        return median;
    }

    // 3rd quartile value -- less noisy upper value
    public double getLast() {
        return last;
    }

    // Total number of measurements
    public int getCount() {
        return count;
    }

    // Type of DataPoint
    public DataPointType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPoint dataPoint = (DataPoint) o;
        return Double.compare(dataPoint.mean, mean) == 0 &&
                Double.compare(dataPoint.first, first) == 0 &&
                Double.compare(dataPoint.median, median) == 0 &&
                Double.compare(dataPoint.last, last) == 0 &&
                count == dataPoint.count &&
                type == dataPoint.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mean, first, median, last, count, type);
    }
}
