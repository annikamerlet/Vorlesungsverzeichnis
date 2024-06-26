package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {

    @Autowired // Vorlesungsverzeichnis erzeugt
    private VorlesungsverzeichnisRepository vorlesungenRepository;
    private List<Vorlesung> vorlesungen;

    public LinkedHashMap<String, LinkedHashMap> erhalteSortierteVorlesungen(String filter, int semester) {

        // je nachdem, aus welchem getMapping die Methode aufgerufen wurde, werden die Vorlesungen anders gefiltert
        List<Vorlesung> alleVorlesungen;
        if (semester > 0) {
            alleVorlesungen = erhalteGefilterteVorlesungen(filter, semester);
        } else {
            alleVorlesungen = erhalteGefilterteVorlesungen(filter);
        }

        //die Bezeichnung für die id's der vorausgesetzten Vorlesungen gespeichert
        for (Vorlesung v : alleVorlesungen) {
            v.setBezeichnungVorausgesetzteVorlesungen(bezeichnungVorausgesetzteVorlesungen(v));
        }

        //Liste: Vorlesungen nach Zeitslot sortiert
        List<Vorlesung> ZeitslotEins = alleVorlesungen.stream().filter(vor -> vor.getUhrzeit().equals(LocalTime.of(8, 30))).collect(Collectors.toList());
        List<Vorlesung> ZeitslotZwei = alleVorlesungen.stream().filter(vor -> vor.getUhrzeit().equals(LocalTime.of(10, 15))).collect(Collectors.toList());


        //Hash-Tabelle: Vorlesung nach Zeitslot und Tag sortiert
        LinkedHashMap<String, List<Vorlesung>> ersterZeitslot = new LinkedHashMap<>();
        ersterZeitslot.put("Montag", erstelleVorlesungenImZeitslot(ZeitslotEins, "Montag"));
        ersterZeitslot.put("Dienstag", erstelleVorlesungenImZeitslot(ZeitslotEins, "Dienstag"));
        ersterZeitslot.put("Mittwoch", erstelleVorlesungenImZeitslot(ZeitslotEins, "Mittwoch"));
        ersterZeitslot.put("Donnerstag", erstelleVorlesungenImZeitslot(ZeitslotEins, "Donnerstag"));
        ersterZeitslot.put("Freitag", erstelleVorlesungenImZeitslot(ZeitslotEins, "Freitag"));

        LinkedHashMap<String, List<Vorlesung>> zweiterZeitslot = new LinkedHashMap<>();
        zweiterZeitslot.put("Montag", erstelleVorlesungenImZeitslot(ZeitslotZwei, "Montag"));
        zweiterZeitslot.put("Dienstag", erstelleVorlesungenImZeitslot(ZeitslotZwei, "Dienstag"));
        zweiterZeitslot.put("Mittwoch", erstelleVorlesungenImZeitslot(ZeitslotZwei, "Mittwoch"));
        zweiterZeitslot.put("Donnerstag", erstelleVorlesungenImZeitslot(ZeitslotZwei, "Donnerstag"));
        zweiterZeitslot.put("Freitag", erstelleVorlesungenImZeitslot(ZeitslotZwei, "Freitag"));

        //Liste aus LinkedHashMaps:
        LinkedHashMap<String, LinkedHashMap> sortierteVorlesungen = new LinkedHashMap<>();
        sortierteVorlesungen.put("8:30Uhr - 10:00Uhr", ersterZeitslot);
        sortierteVorlesungen.put("10:15Uhr - 11:45Uhr", zweiterZeitslot);

        return sortierteVorlesungen;
    }

    // Vorlesungen nach Filterkriterium filtern (1.getMapping)
    private List<Vorlesung> erhalteGefilterteVorlesungen(String filter) {
        if (filter.isEmpty()) {
            vorlesungen = vorlesungenRepository.findAll();
        } else {
            vorlesungen = vorlesungenRepository.findByBezeichnungIgnoreCaseContaining(filter);
        }
        return vorlesungen;
    }

    // Vorlesungen nach Filterkriterium und Semester filtern (2.getMapping)
    private List<Vorlesung> erhalteGefilterteVorlesungen(String filter, int semester) {
        if (filter.isEmpty()) {
            vorlesungen = vorlesungenRepository.findBySemesterEquals(semester);
        } else {
            vorlesungen = vorlesungenRepository.findByBezeichnungIgnoreCaseContainingAndSemesterEquals(filter, semester);
        }
        return vorlesungen;
    }


    private List<Vorlesung> erstelleVorlesungenImZeitslot(List<Vorlesung> vorlesungen, String Wochentag) {
        List<Vorlesung> vorlesungenWochentag = new ArrayList<>();
        for (Vorlesung v : vorlesungen) {
            if (v.getWochentag().equals(Wochentag)) { // !!!
                vorlesungenWochentag.add(v);
            }
        }
        return vorlesungenWochentag;
    }

    //aus Liste von Ids -> Liste von Bezeichnungen
    private List<String> bezeichnungVorausgesetzteVorlesungen(Vorlesung v) {
        List<Long> vorausgesetzteVorlesungenId = v.getVorausgesetzteVorlesungen();
        List<String> vorausgesetzteVorlesungenBezeichnung = new ArrayList<>();
        for (Long id : vorausgesetzteVorlesungenId) {
            vorausgesetzteVorlesungenBezeichnung.add(bezeichnungAusId(id));
        }
        return vorausgesetzteVorlesungenBezeichnung;
    }


    //aus einzelner id -> Bezeichnung
    private String bezeichnungAusId(Long id) {
        Vorlesung vorlesung = vorlesungenRepository.findById(id).get(); //!!!
        return vorlesung.getBezeichnung();
    }

    // boolscher Wert für vorlesung ist ausgewählt invertiert
    public void isVorlesungAusgewaehlt(Long id) {
        Vorlesung vorlesung = vorlesungenRepository.findById(id).get(); //!!!
        vorlesung.setAusgewaehlt(!vorlesung.isAusgewaehlt());
        vorlesungenRepository.save(vorlesung);
    }

}
