package com.example.camundaoauthclientdemo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootPathController {

    @GetMapping({"/"})
    public String startPage() {
        return "startPage";
    }

}
