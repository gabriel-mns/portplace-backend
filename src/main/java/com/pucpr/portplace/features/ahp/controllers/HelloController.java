package com.pucpr.portplace.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hello World", description = "A controller to test if the API is alive")
@RestController
@RequestMapping("/hello-world")
public class HelloController {

    @GetMapping
    public String helloWorld(){

        return "Hello World!";

    }

}
