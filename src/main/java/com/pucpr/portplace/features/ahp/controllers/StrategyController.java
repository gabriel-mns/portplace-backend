package com.pucpr.portplace.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/strategies")
public class StrategyController {

    @GetMapping("/hello-world")
    public String helloWorld(){

        return "Hello World!1";

    }

}
