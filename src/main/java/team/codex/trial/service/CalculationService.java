package team.codex.trial.service;

import team.codex.trial.model.AirportData;

public interface CalculationService {
    double calculateDistance(AirportData ad1, AirportData ad2);
}
