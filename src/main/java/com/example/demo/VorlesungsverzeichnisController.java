package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@Controller // Controller befüllt Model, damit der View (html-Datei) die Daten aus dem Model darstellen kann
@RequestMapping("/Vorlesungsverzeichnis")
public class VorlesungsverzeichnisController {

    @Autowired // Vorlesungsverzeichnis erzeugt
    private Service service;

    @GetMapping
    public String zeigeVorlesungsverzeichnis(Model model, @RequestParam(required = false, defaultValue = "") String alleFilter) {

        LinkedHashMap<String, LinkedHashMap> sortierteVorlesungen = service.erhalteSortierteVorlesungen(alleFilter, 0);
        model.addAttribute("Vorlesungsverzeichnis", sortierteVorlesungen);
        return "Vorlesungsverzeichnis";
    }

    @GetMapping("/semester/{semester}")
    public String zeigeVorlesungsverzeichnisFuerSemester(Model model, @RequestParam(required = false, defaultValue = "") String alleFilter, @PathVariable int semester) {

        LinkedHashMap<String, LinkedHashMap> sortierteVorlesungen = service.erhalteSortierteVorlesungen(alleFilter, semester);
        model.addAttribute("Vorlesungsverzeichnis", sortierteVorlesungen);
        return "Vorlesungsverzeichnis";
    }

    @PostMapping("/save")
    public String post(Model model, @RequestParam long id) {
        service.isCheckBoxAusgewählt(id);
        return "redirect:/Vorlesungsverzeichnis";
    }

}
