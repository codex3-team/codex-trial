package team.codex.trial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import team.codex.trial.service.CalculationService;
import team.codex.trial.service.CalculationServiceImpl;
import team.codex.trial.service.CollectorService;
import team.codex.trial.service.CollectorServiceImpl;
import team.codex.trial.service.QueryService;

import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CalculationService calculationService() {
        return new CalculationServiceImpl();
    }

    @Bean
    CollectorService collectorService() {
        return new CollectorServiceImpl();
    }

    @Bean
    QueryService queryService() {
        return new QueryService();
    }
}
