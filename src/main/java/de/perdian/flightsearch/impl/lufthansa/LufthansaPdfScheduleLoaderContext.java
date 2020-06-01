package de.perdian.flightsearch.impl.lufthansa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.perdian.flightsearch.api.model.Airport;
import de.perdian.flightsearch.api.model.AirportRepository;
import de.perdian.flightsearch.api.model.ScheduleEntry;

class LufthansaPdfScheduleLoaderContext {

    private static final Logger log = LoggerFactory.getLogger(LufthansaPdfScheduleLoaderContext.class);

    private Airport currentOriginAirport = null;
    private Map<String, Airport> currentOriginAirportSelections = null;
    private Airport currentDestinationAirport = null;
    private Map<String, Airport> currentDestinationAirportSelections = null;
    private List<ScheduleEntry> entries = null;

    LufthansaPdfScheduleLoaderContext() {
        this.setEntries(new ArrayList<>());
    }

    void pushOriginAirport(String airportCode, String airportName) {
        this.setCurrentOriginAirport(null);
        this.setCurrentOriginAirportSelections(new LinkedHashMap<>());
        this.setCurrentDestinationAirport(null);
        this.setCurrentDestinationAirportSelections(new LinkedHashMap<>());

        Airport airport  = AirportRepository.getInstance().loadAirportByCode(airportCode);
        if (airport == null) {
            log.error("Cannot find origin airport for code: {} ({})", airportCode, airportName);
        } else {
            log.trace("Found airport: {} ({})", airportCode, airportName);
            this.setCurrentOriginAirport(airport);
            this.setCurrentOriginAirportSelections(new LinkedHashMap<>());
        }
    }

    void pushDestinationAirport(String airportCode, String airportName) {
        this.setCurrentDestinationAirport(null);
        this.setCurrentDestinationAirportSelections(new LinkedHashMap<>());

        if (this.getCurrentOriginAirport() != null) {
            Airport airport = AirportRepository.getInstance().loadAirportByCode(airportCode);
            if (airport == null) {
                log.error("Cannot find destination airport for code: {} ({})", airportCode, airportName);
            } else {
                log.trace("- Computed route: {} » {} ({})", this.getCurrentOriginAirport().getCode(), airportCode, airportName);
                this.setCurrentDestinationAirport(airport);
                this.setCurrentDestinationAirportSelections(new LinkedHashMap<>());
            }
        }
    }

    void pushSelectionAirport(String airportCode, String airportName, String selectionCode) {
        Airport selectionAirport = AirportRepository.getInstance().loadAirportByCode(airportCode);
        if (selectionAirport == null) {
            log.error("- Cannot find selection airport for code: {} ({})", airportCode, airportName);
        } else if (this.getCurrentOriginAirport() != null && this.getCurrentDestinationAirport() != null) {
            log.trace("- Found destination selection airport: {}-{} ({})", this.getCurrentDestinationAirport().getCode(), airportCode, airportName);
            this.getCurrentDestinationAirportSelections().put(selectionCode, selectionAirport);
        } else if (this.getCurrentOriginAirport() != null) {
            log.trace("- Found origin selection airport: {}-{} ({})", this.getCurrentOriginAirport().getCode(), airportCode, airportName);
            this.getCurrentOriginAirportSelections().put(selectionCode, selectionAirport);
        }
    }

    void pushEntry(ScheduleEntry scheduleEntry) {
        this.getEntries().add(scheduleEntry);
    }

    Airport getCurrentOriginAirport() {
        return this.currentOriginAirport;
    }
    private void setCurrentOriginAirport(Airport currentOriginAirport) {
        this.currentOriginAirport = currentOriginAirport;
    }

    Map<String, Airport> getCurrentOriginAirportSelections() {
        return this.currentOriginAirportSelections;
    }
    private void setCurrentOriginAirportSelections(Map<String, Airport> currentOriginAirportSelections) {
        this.currentOriginAirportSelections = currentOriginAirportSelections;
    }

    Airport getCurrentDestinationAirport() {
        return this.currentDestinationAirport;
    }
    private void setCurrentDestinationAirport(Airport currentDestinationAirport) {
        this.currentDestinationAirport = currentDestinationAirport;
    }

    Map<String, Airport> getCurrentDestinationAirportSelections() {
        return this.currentDestinationAirportSelections;
    }
    private void setCurrentDestinationAirportSelections(Map<String, Airport> currentDestinationAirportSelections) {
        this.currentDestinationAirportSelections = currentDestinationAirportSelections;
    }

    List<ScheduleEntry> getEntries() {
        return this.entries;
    }
    private void setEntries(List<ScheduleEntry> entries) {
        this.entries = entries;
    }

}
