package de.szut.shift_backend;

import de.szut.shift_backend.services.InitialDataService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShiftplanBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiftplanBackendApplication.class, args);
    }

}
