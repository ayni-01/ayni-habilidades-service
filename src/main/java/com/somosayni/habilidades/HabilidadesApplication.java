package com.somosayni.habilidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.somosayni.habilidades", "com.somosayni.shared"})
public class HabilidadesApplication {
    public static void main(String[] args) {
        SpringApplication.run(HabilidadesApplication.class, args);
    }
}
