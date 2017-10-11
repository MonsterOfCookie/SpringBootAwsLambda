package com.supercookie.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping(path = "/")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }
}
