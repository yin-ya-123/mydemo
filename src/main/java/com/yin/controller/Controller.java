package com.yin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping(path = "aaa", produces = "application/text;charset=UTF-8")
    public String aaa(String text){
        System.out.println(text );
        return "aaa";
    }
}
