package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Vorlesungsverzeichnis")

public class VorlesungsverzeichnisController {

    @GetMapping
    public String Vorlesungsverzeichnis() {
        return "Vorlesungsverzeichnis";
    }

}
