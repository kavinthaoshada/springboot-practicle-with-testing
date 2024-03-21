package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student oshada = new Student(
                    "oshada",
                    "oshada@gmail.com",
                    LocalDate.of(2000, Month.OCTOBER, 5)
            );
            Student kavintha = new Student(
                    "kavintha",
                    "kavintha@gmail.com",
                    LocalDate.of(2003, Month.OCTOBER, 5)
            );

            repository.saveAll(
                    List.of(oshada, kavintha)
            );
        };
    }
}
