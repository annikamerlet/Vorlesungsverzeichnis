package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller // Controller befüllt Model, damit der View (html-Datei) die Daten aus dem Model darstellen kann
@RequestMapping("/Vorlesungsverzeichnis")

public class VorlesungsverzeichnisController {

    @Autowired // Vorlesungsverzeichnis erzeugt
    private VorlesungsverzeichnisRepository vorlesungen;

    @GetMapping
    public String Vorlesungsverzeichnis(Model model, @RequestParam(required = false, defaultValue = "") String WochentagFilter) {
        model.addAttribute("Vorlesungsverzeichnis",
                vorlesungen.findAll().stream()
                        .filter(w -> (w.getWochentag().contains(WochentagFilter) || w.getBezeichnung().contains(WochentagFilter))).collect(Collectors.toList()));
        return "Vorlesungsverzeichnis";
    }

}
