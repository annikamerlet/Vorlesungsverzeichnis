package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Vorlesung {

    @Id
    @GeneratedValue
    private Long id;
    private String bezeichnung;
    private String wochentag;

    public Vorlesung() {
        // required by hibernate
    }

    public Vorlesung(String bezeichnung, String wochentag) {
        this.bezeichnung = bezeichnung;
        this.wochentag = wochentag;
    }

    public Long getId() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getWochentag() {
        return wochentag;
    }

}
