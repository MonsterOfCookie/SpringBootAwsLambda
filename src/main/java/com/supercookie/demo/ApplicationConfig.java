package com.supercookie.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ApplicationConfig {

    /*
        There are multiple ways we could achieve this, with classpath scanning etc.
        However note with that scanning, to narrow the field of search, scanning on Lambda (12/10/2017) is not the fastest
     */
    @Bean
    public HelloController helloController() {
        return new HelloController();
    }

}
