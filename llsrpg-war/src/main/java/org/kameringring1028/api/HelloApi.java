package org.kameringring1028.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloApi {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello world";
    }
}