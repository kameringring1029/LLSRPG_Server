package org.kameringring1028;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.kameringring1028")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}