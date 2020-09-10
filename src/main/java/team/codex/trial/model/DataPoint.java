package team.codex.trial.model;

/**
 * A collected point, including some information about the range of collected values
 *
 * @author code test administrator
 */
public class DataPoint {

  private double mean = 0;
  private double first = 0;
  private double median = 0;
  private double last = 0;
  private int count = 0;
  private DataPointType type = null;

  /**
   * private constructor, use the builder to create this object
   */
  private DataPoint() {
  }

  public DataPoint(double mean, double first, double median, double last, int count,
      DataPointType type) {
    this.mean = mean;
    this.first = first;
    this.median = median;
    this.last = last;
    this.count = count;
    this.type = type;
  }

  /**
   * the mean of the observations
   */
  public double getMean() {
    return mean;
  }

  /**
   * 1st quartile -- useful as a lower bound
   */
  public double getFirst() {
    return first;
  }

  /**
   * 2nd quartile -- median value
   */
  public double getMedian() {
    return median;
  }

  /**
   * 3rd quartile value -- less noisy upper value
   */
  public double getLast() {
    return last;
  }

  /**
   * the total number of measurements
   */
  public int getCount() {
    return count;
  }

  /**
   * type of DataPoint
   */
  public DataPointType getType() {
    return type;
  }
}
