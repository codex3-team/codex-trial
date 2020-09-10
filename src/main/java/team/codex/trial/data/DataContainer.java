package team.codex.trial.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import team.codex.trial.model.AirportData;
import team.codex.trial.model.AtmosphericInformation;

public class DataContainer {

  private final static ReadWriteLock lock = new ReentrantReadWriteLock();
  private final static Lock readLock = lock.readLock();
  private final static Lock writeLock = lock.writeLock();

  /**
   * all known airports
   */
  private final static List<AirportData> airportData = Collections
      .synchronizedList(new ArrayList<>());

  /**
   * Internal performance counter to better understand most requested information, this map can be
   * improved but for now provides the basis for future performance optimizations. Due to the
   * stateless deployment architecture we don't want to write this to disk, but will pull it off
   * using a REST request and aggregate with other performance metrics
   */
  private final static Map<AirportData, Integer> requestFrequency = new HashMap<>();

  private final static Map<Double, Integer> radiusFreq = new HashMap<>();

  public static AirportData addAirport(String iata, int latitude, int longitude) {
    try {
      writeLock.lock();
      AirportData data = new AirportData(iata, latitude, longitude, new AtmosphericInformation());
      airportData.add(data);
      return data;
    } finally {
      writeLock.unlock();
    }
  }

  public static boolean deleteAirport(String iata) {
    try {
      writeLock.lock();
      return airportData.removeIf(item -> item.getIata().equals(iata));
    } finally {
      writeLock.unlock();
    }
  }

  public static void updateRequestFrequency(AirportData airportData, Double radius) {
    try {
      writeLock.lock();
      requestFrequency.put(airportData, requestFrequency.getOrDefault(airportData, 0) + 1);
      radiusFreq.put(radius, radiusFreq.getOrDefault(radius, 0) + 1);
    } finally {
      writeLock.unlock();
    }
  }

  public static List<AirportData> getAirportData() {
    try {
      readLock.lock();
      return Collections.unmodifiableList(airportData);
    } finally {
      readLock.unlock();
    }
  }

  public static Map<AirportData, Integer> getRequestFrequency() {
    try {
      readLock.lock();
      return Collections.unmodifiableMap(requestFrequency);
    } finally {
      readLock.unlock();
    }
  }

  public static Map<Double, Integer> getRadiusFreq() {
    try {
      readLock.lock();
      return Collections.unmodifiableMap(radiusFreq);
    } finally {
      readLock.unlock();
    }
  }
}