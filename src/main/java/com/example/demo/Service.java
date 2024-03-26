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

    private List<Vorlesung> erhalteGefilterteVorlesungen(String filter, int semester) {

        List<String> filterListe = List.of(filter.toLowerCase().split(" ")); // trennt Eingabe nach Leerzeichen und speichert diese in Liste
        List<Vorlesung> vorlesungen = vorlesungenRepository.findBySemesterEquals(semester); // Liste mit allen Vorlesungen

        for (String element : filterListe) // über Filter schleifen: passt die Liste zunächst auf das 1.Listenelement an, dann auf das 2, 3., ... (so viele Listeneinträge es gibt)
        {
            vorlesungen = vorlesungen.stream()
                    .filter(w -> (w.getWochentag().toLowerCase().contains(element) || w.getBezeichnung().toLowerCase().contains(element))).collect(Collectors.toList());
        }

        return vorlesungen;
    }

    private List<Vorlesung> erhalteGefilterteVorlesungen(String filter) {

        List<String> filterListe = List.of(filter.toLowerCase().split(" ")); // trennt Eingabe nach Leerzeichen und speichert diese in Liste
        List<Vorlesung> vorlesungen = vorlesungenRepository.findAll(); // Liste mit allen Vorlesungen

        for (String element : filterListe) // über Filter schleifen: passt die Liste zunächst auf das 1.Listenelement an, dann auf das 2, 3., ... (so viele Listeneinträge es gibt)
        {
            vorlesungen = vorlesungen.stream()
                    .filter(w -> (w.getWochentag().toLowerCase().contains(element) || w.getBezeichnung().toLowerCase().contains(element))).collect(Collectors.toList());
        }

        return vorlesungen;
    }

    public LinkedHashMap<String, LinkedHashMap> erhalteSortierteVorlesungen(String filter, int semester) {
        List<Vorlesung> alleVorlesungen;
        if (semester > 0) {
            alleVorlesungen = erhalteGefilterteVorlesungen(filter, semester);
        } else {
            alleVorlesungen = erhalteGefilterteVorlesungen(filter);
        }

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

    private List<Vorlesung> erstelleVorlesungenImZeitslot(List<Vorlesung> vorlesungen, String Wochentag) {

        List<Vorlesung> vorlesungenWochentag = new ArrayList<>();
        for (Vorlesung v : vorlesungen) {
            if (v.getWochentag().equals(Wochentag)) {
                vorlesungenWochentag.add(v);
            }
        }
        return vorlesungenWochentag;
    }

    //aus Liste von Ids -> Liste von Bezeichnungen
    public List<String> bezeichnungVorausgesetzteVorlesungen(Vorlesung v) {
        List<Long> vorausgesetzteVorlesungenId = v.getVorausgesetzteVorlesungen();
        List<String> vorausgesetzteVorlesungenBezeichnung = new ArrayList<>();
        for (Long id : vorausgesetzteVorlesungenId) {
            vorausgesetzteVorlesungenBezeichnung.add(bezeichnungAusId(id));
        }
        return vorausgesetzteVorlesungenBezeichnung;
    }

    private String bezeichnungAusId(Long id) {
        Vorlesung vorlesung = vorlesungenRepository.findById(id).get();
        return vorlesung.getBezeichnung();
    }

    public void isCheckBoxAusgewählt(Long id) {
        Vorlesung vorlesung = vorlesungenRepository.findById(id).get();
        vorlesung.setAusgewaehlt(!vorlesung.isAusgewaehlt());
        vorlesungenRepository.save(vorlesung);
    }

}
