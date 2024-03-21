package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Controller befüllt Model, damit der View (html-Datei) die Daten aus dem Model darstellen kann
@RequestMapping("/Vorlesungsverzeichnis")

public class VorlesungsverzeichnisController {

    @Autowired // Vorlesungsverzeichnis erzeugt
    private VorlesungsverzeichnisRepository vorlesungen;

    @GetMapping
    public String Vorlesungsverzeichnis(Model model) {
        model.addAttribute("Vorlesungsverzeichnis", vorlesungen.findAll().stream());
        return "Vorlesungsverzeichnis";
    }

}
