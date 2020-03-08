package de.perdian.flightsearch.api.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirportRepository {

    private static final Logger log = LoggerFactory.getLogger(AirportRepository.class);
    private static final AirportRepository instance = new AirportRepository();
    private static final char OPENFLIGHTS_FIELD_DELIMITER = '\"';

    private Map<String, Airport> airportsByCode = null;

    public static AirportRepository getInstance() {
        return AirportRepository.instance;
    }

    private AirportRepository() {
        try {

            URL countriesResourceURL = AirportRepository.class.getResource("/META-INF/flightsearch-api/countries.dat");
            log.debug("Loading countries from resource: {}", countriesResourceURL);
            Map<String, String> countryCodesByTitle = new LinkedHashMap<>();
            try (BufferedReader countriesReader = new BufferedReader(new InputStreamReader(countriesResourceURL.openStream(), "UTF-8"))) {
                for (String countryLine = countriesReader.readLine(); countryLine != null; countryLine = countriesReader.readLine()) {
                    List<String> countryFields = AirportRepository.tokenizeLine(countryLine);
                    String countryName = countryFields.get(0);
                    String countryCode = countryFields.get(2);
                    countryCodesByTitle.put(countryName, countryCode);
                }
            }
            log.debug("Loaded {} countries from resource: {}", countryCodesByTitle.size(), countriesResourceURL);

            URL airportsResourceURL = AirportRepository.class.getResource("/META-INF/flightsearch-api/airports-extended.dat");
            log.debug("Loading airports from resource: {}", airportsResourceURL);
            Map<String, Airport> airportsByCode = new LinkedHashMap<>();
            try (BufferedReader airportsReader = new BufferedReader(new InputStreamReader(airportsResourceURL.openStream(), "UTF-8"))) {
                for (String airportLine = airportsReader.readLine(); airportLine != null; airportLine = airportsReader.readLine()) {
                    try {

                        List<String> lineFields = AirportRepository.tokenizeLine(airportLine);
                        String iataCode = lineFields.get(4);
                        ZoneOffset zoneOffset = null;
                        String zoneOffsetString = lineFields.get(9);
                        if (zoneOffsetString != null && !zoneOffsetString.equals("\\N")) {
                            float zoneOffsetValue = Float.parseFloat(lineFields.get(9));
                            int zoneOffsetHours = (int)zoneOffsetValue;
                            int zoneOffsetMinutes = (int)((Math.abs(zoneOffsetValue) - Math.abs(zoneOffsetHours)) * 60 * Math.signum(zoneOffsetValue));
                            zoneOffset = ZoneOffset.ofHoursMinutes(zoneOffsetHours, zoneOffsetMinutes);
                        }

                        String zoneIdValue = lineFields.get(11);
                        ZoneId zoneId = zoneIdValue == null || zoneIdValue.equalsIgnoreCase("\\N") ? null : ZoneId.of(zoneIdValue);

                        Airport airport = new Airport();
                        airport.setName(lineFields.get(1));
                        airport.setCity(lineFields.get(2));
                        airport.setCountryCode(countryCodesByTitle.get(lineFields.get(3)));
                        airport.setCode(iataCode);
                        airport.setLatitude(Float.parseFloat(lineFields.get(6)));
                        airport.setLongitude(Float.parseFloat(lineFields.get(7)));
                        airport.setTimezoneOffset(zoneOffset);
                        airport.setTimezoneId(zoneId);
                        airport.setType(AirportType.parseValue(lineFields.get(12)));
                        airportsByCode.put(iataCode, airport);

                    } catch (Exception e) {
                        log.warn("Invalid airport line: {}", airportLine, e);
                    }
                }
            }

            this.setAirportsByCode(airportsByCode);
            log.debug("Loaded {} airports from resource: {}", airportsByCode.size(), airportsResourceURL);

        } catch (IOException e) {
            throw new RuntimeException("Cannot initialilze AirportRepository", e);
        }
    }

    public Airport loadAirportByCode(String airportCode) {
        return StringUtils.isEmpty(airportCode) ? null : this.getAirportsByCode().get(airportCode);
    }

    private static List<String> tokenizeLine(String line) {
        String[] lineValues = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        List<String> resultValues = new ArrayList<>(lineValues.length);
        for (String lineValue : lineValues) {
            int startIndex = lineValue.startsWith(String.valueOf(OPENFLIGHTS_FIELD_DELIMITER)) ? 1 : 0;
            int endIndex = lineValue.endsWith(String.valueOf(OPENFLIGHTS_FIELD_DELIMITER)) ? lineValue.length() - 1 : lineValue.length();
            resultValues.add(lineValue.substring(startIndex, endIndex));
        }
        return Collections.unmodifiableList(resultValues);
    }

    private Map<String, Airport> getAirportsByCode() {
        return this.airportsByCode;
    }
    private void setAirportsByCode(Map<String, Airport> airportsByCode) {
        this.airportsByCode = airportsByCode;
    }

}
