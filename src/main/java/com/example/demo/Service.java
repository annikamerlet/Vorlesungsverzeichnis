package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {
    @Autowired // Vorlesungsverzeichnis erzeugt
    private VorlesungsverzeichnisRepository vorlesungenRepository;

    public List<Vorlesung> erhalteGefilterteVorlesungen(String filter) {
        List<String> filterListe = List.of(filter.toLowerCase().split(" ")); // trennt Eingabe nach Leerzeichen und speichert diese in Liste
        List<Vorlesung> vorlesungen = vorlesungenRepository.findAll(); // Liste mit allen Vorlesungen
        for (String element : filterListe) // passt die Liste zunächst auf das 1.Listenelement an, dann auf das 2, 3., ... (so viele Listeneinträge es gibt)
        {
            vorlesungen = vorlesungen.stream()
                    .filter(w -> (w.getWochentag().toLowerCase().contains(element) || w.getBezeichnung().toLowerCase().contains(element))).collect(Collectors.toList());
        }
        return vorlesungen;
    }
}
