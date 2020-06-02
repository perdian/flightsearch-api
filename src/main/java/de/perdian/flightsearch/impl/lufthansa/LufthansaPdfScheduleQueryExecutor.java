package de.perdian.flightsearch.impl.lufthansa;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.perdian.flightsearch.api.ScheduleQueryExecutor;
import de.perdian.flightsearch.api.model.Aircraft;
import de.perdian.flightsearch.api.model.AircraftType;
import de.perdian.flightsearch.api.model.AircraftTypeRepository;
import de.perdian.flightsearch.api.model.Airline;
import de.perdian.flightsearch.api.model.Airport;
import de.perdian.flightsearch.api.model.AirportContact;
import de.perdian.flightsearch.api.model.FlightNumber;
import de.perdian.flightsearch.api.model.Leg;
import de.perdian.flightsearch.api.model.Route;
import de.perdian.flightsearch.api.model.Schedule;
import de.perdian.flightsearch.api.model.ScheduleEntry;
import de.perdian.flightsearch.api.model.ScheduleQuery;
import de.perdian.flightsearch.api.model.Segment;

public class LufthansaPdfScheduleQueryExecutor implements ScheduleQueryExecutor {

    private static Pattern ORIGIN_AIRPORT_LINE_PATTERN = Pattern.compile("^(.*?)\\s+([+-]\\d{2}\\:\\d{2})([A-Z]{3})$");
    private static Pattern DESTINATION_AIRPORT_LINE_PATTERN = Pattern.compile("^\u2192\\s+(.*?)\\s([A-Z]{3})\\s+([+-]\\d{2}\\:\\d{2})$");
    private static Pattern DESTINATION_AIRPORT_REPEATER_LINE_PATTERN = Pattern.compile("^→\\s+(.*?)$");
    private static Pattern SELECTION_AIRPORT_LINE_PATTERN = Pattern.compile("^([A-Z]{3})\\s+(.*?)\\s*([A-Z]?)$");
    private static Pattern ENTRY_PATTERN = Pattern.compile("^([X1-7]+)\\s+([A-Z]?\\d{2}[A-Z]?\\:\\d{2})\\s*(\\d{2}\\:\\d{2}[A-Z]?)\\+?\\s([A-Z0-9]{2}[0-9]+.*?)\\s+([A-Z0-9]{3,4})\\s*$");
    private static Pattern SOURCE_FILE_PATTERN = Pattern.compile("^V1_(.*?)_to_(.*?)_.*?\\.pdf$");
    private static Pattern FLIGHTNUMBER_DIRECT_PATTERN = Pattern.compile("^([A-Z0-9]{2})([0-9]+)$");
    private static Pattern FLIGHTNUMBER_CODESHARE_PATTERN = Pattern.compile("^([A-Z0-9]{2})([0-9]+)\\(([A-Z0-9]{2})\\)$");

    private static final Logger log = LoggerFactory.getLogger(LufthansaPdfScheduleQueryExecutor.class);
    private File sourceFile = null;

    public LufthansaPdfScheduleQueryExecutor(File sourceFile) {
        this.setSourceFile(sourceFile);
    }

    @Override
    public Schedule loadSchedule(ScheduleQuery scheduleQuery) throws IOException {

        LocalDate validFrom = null;
        LocalDate validTo = null;
        String sourceFileName = this.getSourceFile().getName();
        Matcher sourceFileMatcher = SOURCE_FILE_PATTERN.matcher(sourceFileName);
        if (sourceFileMatcher.matches()) {
            validFrom = LocalDate.parse(sourceFileMatcher.group(1));
            validTo = LocalDate.parse(sourceFileMatcher.group(2));
        }

        try (PDDocument pdfDocument = PDDocument.load(this.getSourceFile())) {

            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(6);
            pdfStripper.setEndPage(56);
            String pdfContent = pdfStripper.getText(pdfDocument);

            LufthansaPdfScheduleLoaderContext loaderContext = new LufthansaPdfScheduleLoaderContext();
            try (BufferedReader pdfLineReader = new BufferedReader(new StringReader(pdfContent))) {
                for (String line = pdfLineReader.readLine(); line != null; line = pdfLineReader.readLine()) {

                    if ("Frq Dep Arr Flight Aircraft Frq Dep Arr Flight Aircraft Frq Dep Arr Flight Aircraft".equalsIgnoreCase(line)) {
                        continue;
                    }

                    Matcher originAirportLineMatcher = ORIGIN_AIRPORT_LINE_PATTERN.matcher(line);
                    if (originAirportLineMatcher.matches()) {
                        String airportName = originAirportLineMatcher.group(1);
                        String airportCode = originAirportLineMatcher.group(3);
                        loaderContext.pushOriginAirport(airportCode, airportName);
                        continue;
                    }

                    Matcher destinationAirportLineMatcher = DESTINATION_AIRPORT_LINE_PATTERN.matcher(line);
                    if (destinationAirportLineMatcher.matches()) {
                        String airportName = destinationAirportLineMatcher.group(1);
                        String airportCode = destinationAirportLineMatcher.group(2);
                        loaderContext.pushDestinationAirport(airportCode, airportName);
                        continue;
                    }

                    Matcher destinationAirportRepeaterLineMatcher = DESTINATION_AIRPORT_REPEATER_LINE_PATTERN.matcher(line);
                    if (destinationAirportRepeaterLineMatcher.matches()) {
                        continue;
                    }

                    Matcher selectionAirportLineMatcher = SELECTION_AIRPORT_LINE_PATTERN.matcher(line);
                    if (selectionAirportLineMatcher.matches()) {
                        String airportName = selectionAirportLineMatcher.group(2);
                        String airportCode = selectionAirportLineMatcher.group(1);
                        String airportSelectionCode = selectionAirportLineMatcher.group(3);
                        loaderContext.pushSelectionAirport(airportCode, airportName, airportSelectionCode);
                        continue;
                    }

                    Matcher entryMatcher = ENTRY_PATTERN.matcher(line);
                    if (entryMatcher.matches()) {
                        this.parseScheduleEntry(entryMatcher, validFrom, validTo, loaderContext);
                        continue;
                    }

                    log.error("Invalid line found: {}", line);

                }
            }

            List<ScheduleEntry> scheduleEntries = loaderContext.getEntries().stream()
                .filter(entry -> scheduleQuery.getEntry() == null || scheduleQuery.getEntry().test(entry))
                .collect(Collectors.toList());

            Schedule schedule = new Schedule();
            schedule.setSource(this.getSourceFile().getName());
            schedule.setEntries(scheduleEntries);
            return schedule;

        }
    }

    private void parseScheduleEntry(Matcher entryMatcher, LocalDate validFrom, LocalDate validTo, LufthansaPdfScheduleLoaderContext loaderContext) {

        Airport originAirport = loaderContext.getCurrentOriginAirport();
        String departureTimeString = entryMatcher.group(2);
        LocalTime departureTime = null;
        if (departureTimeString.length() > 5) {
            originAirport = loaderContext.getCurrentOriginAirportSelections().get(departureTimeString.substring(0, 1));
            departureTime = LocalTime.parse(departureTimeString.substring(1, 6));
        } else {
            departureTime = LocalTime.parse(departureTimeString.substring(0, 5));
        }

        Airport destinationAirport = loaderContext.getCurrentDestinationAirport();
        String arrivalTimeString = entryMatcher.group(3);
        if (arrivalTimeString.length() > 5) {
            destinationAirport = loaderContext.getCurrentDestinationAirportSelections().get(arrivalTimeString.substring(5, 6));
        }
        LocalTime arrivalTime = LocalTime.parse(arrivalTimeString.substring(0, 5));

        if (originAirport == null) {
            log.warn("Cannot find origin airport for entry: {} (current origin: {})", entryMatcher.group(), loaderContext.getCurrentOriginAirport());
        } else if (destinationAirport == null) {
            log.warn("Cannot find destination airport for entry: {} (current destination: {})", entryMatcher.group(), loaderContext.getCurrentDestinationAirport());
        } else {

            String aircraftTypeString = entryMatcher.group(5);
            AircraftType aircraftType = AircraftTypeRepository.getInstance().loadAircraftTypeByCode(aircraftTypeString);
            if (aircraftType == null) {
                aircraftType = new AircraftType(aircraftTypeString);
            }
            AirportContact originContact = new AirportContact();
            originContact.setAirport(originAirport);
            originContact.setLocalTime(departureTime);
            AirportContact destinationContact = new AirportContact();
            destinationContact.setAirport(destinationAirport);
            destinationContact.setLocalTime(arrivalTime);
            Leg leg = new Leg();
            leg.setScheduledRoute(new Route(originContact, destinationContact));
            leg.setAircraft(new Aircraft(aircraftType));

            Segment segment = new Segment();
            segment.setLegs(Arrays.asList(leg));

            String flightNumberString = entryMatcher.group(4);
            Matcher flightNumberCodeshareMatcher = FLIGHTNUMBER_CODESHARE_PATTERN.matcher(flightNumberString);
            Matcher flightNumberDirectMatcher = FLIGHTNUMBER_DIRECT_PATTERN.matcher(flightNumberString);
            if (flightNumberCodeshareMatcher.matches()) {
                String marketingAirlineCode = flightNumberCodeshareMatcher.group(1);
                int marketingNumber = Integer.parseInt(flightNumberCodeshareMatcher.group(2), 10);
                String operatingAirlineCode = flightNumberCodeshareMatcher.group(3);
                segment.setMarketingFlightNumber(new FlightNumber(new Airline(marketingAirlineCode), marketingNumber, null));
                segment.setOperatingAirline(new Airline(operatingAirlineCode));
            } else if (flightNumberDirectMatcher.matches()) {
                FlightNumber flightNumber = new FlightNumber(new Airline(flightNumberDirectMatcher.group(1)), Integer.parseInt(flightNumberDirectMatcher.group(2), 10), null);
                segment.setMarketingFlightNumber(flightNumber);
                segment.setOperatingFlightNumber(flightNumber);
                segment.setOperatingAirline(flightNumber.getAirline());
            } else {
                throw new IllegalArgumentException("Invalid flight numnber: " + flightNumberString);
            }

            ScheduleEntry scheduleEntry = new ScheduleEntry();
            scheduleEntry.setSegment(segment);
            scheduleEntry.setValidFrom(validFrom);
            scheduleEntry.setValidTo(validTo);
            scheduleEntry.setDays(this.parseDays(entryMatcher.group(1)));
            loaderContext.pushEntry(scheduleEntry);

        }
    }

    private List<DayOfWeek> parseDays(String value) {
        if ("X".equalsIgnoreCase(value)) {
            return Arrays.asList(DayOfWeek.values());
        } else if (value.startsWith("X")) {
            List<DayOfWeek> days = new ArrayList<>(Arrays.asList(DayOfWeek.values()));
            for (char dayChar : value.substring(1).toCharArray()) {
                days.remove(DayOfWeek.of(Integer.parseInt(String.valueOf(dayChar))));
            }
            return days;
        } else {
            List<DayOfWeek> days = new ArrayList<>();
            for (char dayChar : value.toCharArray()) {
                days.add(DayOfWeek.of(Integer.parseInt(String.valueOf(dayChar))));
            }
            return days;
        }
    }

    private File getSourceFile() {
        return this.sourceFile;
    }
    private void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

}
