package io.singh1114.springboottut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class RestAPI {
    public static void main(String[] args) {
        SpringApplication.run(RestAPI.class, args);
    }
}
