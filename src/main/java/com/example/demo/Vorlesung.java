package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
public class Vorlesung {

    @Id
    @GeneratedValue
    private Long id;
    private String bezeichnung;
    private String wochentag;
    private LocalTime uhrzeit;

    @ElementCollection
    @CollectionTable(
            name = "vorausgesetzte_Vorlesungen",
            joinColumns = {@JoinColumn(name = "id")}
    )
    @Column(name = "VORAUSGESETZTE_VORLESUNGEN_ID")
    private List<Long> vorausgesetzteVorlesungen;


    @Transient
    private List<String> bezeichnungVorausgesetzteVorlesungen;

    private Integer semester;
    private boolean ausgewaehlt;

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

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public boolean isAusgewaehlt() {
        return ausgewaehlt;
    }

    public void setAusgewaehlt(boolean ausgewaehlt) {
        this.ausgewaehlt = ausgewaehlt;
    }

    public LocalTime getUhrzeit() {
        return uhrzeit;
    }

//    public void setUhrzeit(String zeit) {
//        List<Integer> zahlen = List.of(Integer.valueOf(zeit.split(":")));
//        int stunde = zahlen.get(0);
//        int minute = zahlen.get(1);
//        this.uhrzeit = LocalTime.of(stunde, minute);
//    }

    public void setUhrzeit(int stunde, int minute) {
        this.uhrzeit = LocalTime.of(stunde, minute);
    }

    public List<Long> getVorausgesetzteVorlesungen() {
        return vorausgesetzteVorlesungen;
    }

    public void setVorausgesetzteVorlesungen(List<Long> vorausgesetzteVorlesungen) {
        this.vorausgesetzteVorlesungen = vorausgesetzteVorlesungen;
    }

    public List<String> getBezeichnungVorausgesetzteVorlesungen() {
        return bezeichnungVorausgesetzteVorlesungen;
    }

    public void setBezeichnungVorausgesetzteVorlesungen(List<String> bezeichnungVorausgesetzteVorlesungen) {
        this.bezeichnungVorausgesetzteVorlesungen = bezeichnungVorausgesetzteVorlesungen;
    }

    @Override
    public String toString() {
        return " Vorlesung: " + this.getBezeichnung()
                + " Wochentag: " + this.getWochentag();
    }

}
