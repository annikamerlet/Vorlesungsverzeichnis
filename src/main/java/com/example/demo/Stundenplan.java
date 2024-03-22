package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Stundenplan {

    private final List<Vorlesung> vorlesungen; // Liste mit allen Vorlesungen
    @Autowired // Vorlesungsverzeichnis erzeugt
    private VorlesungsverzeichnisRepository vorlesungenRepository;

    public Stundenplan(List<Vorlesung> vorlesungen) {
        this.vorlesungen = vorlesungen.stream()
                .sorted()
                .toList();
    }

    public List<Vorlesung> getVorlesungen() {
        return vorlesungen;
    }


}
