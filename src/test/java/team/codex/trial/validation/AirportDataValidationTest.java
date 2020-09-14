package team.codex.trial.validation;

import org.junit.Test;
import team.codex.trial.model.DST;
import team.codex.trial.model.ExternalAirportData;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertTrue;

public class AirportDataValidationTest {

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void validate() {
        var ad = new ExternalAirportData("", "country",
                "AAA", "AAAA", 12, 2, 1, 1, DST.A);
        assertProperty(ad, "city");

        ad = new ExternalAirportData("city", "",
                "AAA", "AAAA", 12, 2, 1, 1, DST.A);
        assertProperty(ad, "country");

        ad = new ExternalAirportData("city", "country",
                "AAA123", "AAAA", 12, 2, 1, 1, DST.A);
        assertProperty(ad, "iata");

        ad = new ExternalAirportData("city", "country",
                "AAA", "AAAA123", 12, 2, 1, 1, DST.A);
        assertProperty(ad, "icao");

        ad = new ExternalAirportData("city", "country",
                "AAA", "AAAA", 1234567, 2, 1, 1, DST.A);
        assertProperty(ad, "latitude");

        ad = new ExternalAirportData("city", "country",
                "AAA", "AAAA", 123456, 1234567, 1, 1, DST.A);
        assertProperty(ad, "longitude");

        ad = new ExternalAirportData("city", "country",
                "AAA", "AAAA", 123456, 1234567, -1, 1, DST.A);
        assertProperty(ad, "altitude");

        ad = new ExternalAirportData("city", "country",
                "AAA", "AAAA", 123456, 1234567, -1, 123.123, DST.A);
        assertProperty(ad, "tzOffset");
    }

    private void assertProperty(ExternalAirportData data, String failedProperty) {
        var constraints = validator.validate(data);
        assertTrue(constraints.stream().filter(c -> c.getPropertyPath().toString().equals(failedProperty)).findFirst().isPresent());
    }
}
