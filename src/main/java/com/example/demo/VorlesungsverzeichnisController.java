package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller // Controller befüllt Model, damit der View (html-Datei) die Daten aus dem Model darstellen kann
@RequestMapping("/Vorlesungsverzeichnis")

public class VorlesungsverzeichnisController {

    @Autowired // Vorlesungsverzeichnis erzeugt
    private VorlesungsverzeichnisRepository vorlesungenRepository;

    @GetMapping
    public String Vorlesungsverzeichnis(Model model, @RequestParam(required = false, defaultValue = "") String alleFilter) {

        List<String> filterListe = List.of(alleFilter.split(" ")); // trennt Eingabe nach Leerzeichen und speichert diese in Liste
        List<Vorlesung> vorlesungen = vorlesungenRepository.findAll(); // Liste mit allen Vorlesungen

        for (String element : filterListe) {
            vorlesungen = vorlesungen.stream()
                    .filter(w -> (w.getWochentag().contains(element) || w.getBezeichnung().contains(element))).collect(Collectors.toList());
        }

        model.addAttribute("Vorlesungsverzeichnis", vorlesungen);
        return "Vorlesungsverzeichnis";
    }

}
