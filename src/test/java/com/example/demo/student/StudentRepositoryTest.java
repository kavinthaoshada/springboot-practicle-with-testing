package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindStudentByEmail() {
        // given
        String email = "nimal@gmail.com";
        Student student = new Student(
                "nimal",
                email,
                LocalDate.of(2000, Month.OCTOBER, 5)
        );
        underTest.save(student);

        // when
        Optional<Student> expected = underTest.findStudentByEmail(email);

        // then
        assertThat(expected).isPresent();
    }

    @Test
    void itShouldFindStudentIfNotExistByEmail() {
        // given
        String email = "nimal@gmail.com";

        // when
        Optional<Student> expected = underTest.findStudentByEmail(email);

        // then
        assertThat(expected).isNotPresent();
    }
}