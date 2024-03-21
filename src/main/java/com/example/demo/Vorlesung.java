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

    public void setId(Long id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getWochentag() {
        return wochentag;
    }

    public void setWochentag(String wochentag) {
        this.wochentag = wochentag;
    }

    @Override
    public String toString() {
        return this.getId().toString() + "Vorlesung" + this.getBezeichnung()
                + " Wochentag" + this.getWochentag() + "\\n";
    }

}
