package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

@Controller // Controller befüllt Model, damit der View (html-Datei) die Daten aus dem Model darstellen kann
@RequestMapping("/Vorlesungsverzeichnis")

public class VorlesungsverzeichnisController {

    @Autowired // Vorlesungsverzeichnis erzeugt
    private Service service;

    @GetMapping
    public String Vorlesungsverzeichnis(Model model, @RequestParam(required = false, defaultValue = "") String alleFilter) {

        List<LinkedHashMap> sortierteVorlesungen = service.erhalteSortierteVorlesungen(alleFilter);
        model.addAttribute("Vorlesungsverzeichnis", sortierteVorlesungen);

        return "Vorlesungsverzeichnis";
    }

    @PostMapping
    public String post(Model model, @RequestParam Long Id) {

        return "Vorlesungsverzeichnis";
    }

}
