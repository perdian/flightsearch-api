package de.perdian.flightsearch.api.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirlineRepository {

    private static final Logger log = LoggerFactory.getLogger(AirlineRepository.class);
    private static final AirlineRepository instance = new AirlineRepository();
    private static final char OPENFLIGHTS_FIELD_DELIMITER = '\"';

    private Map<String, Airline> airlinesByCode = null;

    public static AirlineRepository getInstance() {
        return AirlineRepository.instance;
    }

    private AirlineRepository() {
        try {

            URL countriesResourceURL = AirlineRepository.class.getResource("/META-INF/flightsearch-api/countries.dat");
            log.debug("Loading countries from resource: {}", countriesResourceURL);
            Map<String, String> countryCodesByTitle = new LinkedHashMap<>();
            try (BufferedReader countriesReader = new BufferedReader(new InputStreamReader(countriesResourceURL.openStream(), "UTF-8"))) {
                for (String countryLine = countriesReader.readLine(); countryLine != null; countryLine = countriesReader.readLine()) {
                    List<String> countryFields = AirlineRepository.tokenizeLine(countryLine);
                    String countryName = countryFields.get(0);
                    String countryCode = countryFields.get(2);
                    countryCodesByTitle.put(countryName, countryCode);
                }
            }
            log.debug("Loaded {} countries from resource: {}", countryCodesByTitle.size(), countriesResourceURL);

            URL airlinesResourceURL = AirlineRepository.class.getResource("/META-INF/flightsearch-api/airlines.dat");
            log.debug("Loading airlines from resource: {}", airlinesResourceURL);
            Map<String, Airline> airlinesByCode = new LinkedHashMap<>();
            try (BufferedReader airlinesReader = new BufferedReader(new InputStreamReader(airlinesResourceURL.openStream(), "UTF-8"))) {
                for (String airlineLine = airlinesReader.readLine(); airlineLine != null; airlineLine = airlinesReader.readLine()) {
                    try {
                        List<String> lineFields = AirlineRepository.tokenizeLine(airlineLine);
                        String alias = lineFields.get(2);
                        Airline airline = new Airline();
                        airline.setName(lineFields.get(1));
                        airline.setAlias("\\N".equalsIgnoreCase(alias) ? null : alias);
                        airline.setCode(lineFields.get(3));
                        airline.setCallsign(lineFields.get(5));
                        airline.setCountryCode(countryCodesByTitle.get(lineFields.get(6)));
                        airlinesByCode.putIfAbsent(lineFields.get(3), airline);
                    } catch (Exception e) {
                        log.warn("Invalid airline line: {}", airlineLine, e);
                    }
                }
            }

            this.setAirlinesByCode(airlinesByCode);
            log.debug("Loaded {} airlines from resource: {}", airlinesByCode.size(), airlinesResourceURL);

        } catch (IOException e) {
            throw new RuntimeException("Cannot initialilze AirlineRepository", e);
        }
    }

    public Airline loadAirlineByCode(String airlineCode) {
        return StringUtils.isEmpty(airlineCode) ? null : this.getAirlinesByCode().get(airlineCode);
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

    private Map<String, Airline> getAirlinesByCode() {
        return this.airlinesByCode;
    }
    private void setAirlinesByCode(Map<String, Airline> airlinesByCode) {
        this.airlinesByCode = airlinesByCode;
    }

}
