package com.blog.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/*
Cada request que recibe el servicio/ip sin forma o lugar se va a redireccionar
directamente al lugar inicial --> administrado por HomeController, que /home

La función de la clase es evitar peticiones sin rumbo o lo que vendrían a ser,
evitar errrores 401-500.
 */
@Controller
public class RedirectController {
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
}
