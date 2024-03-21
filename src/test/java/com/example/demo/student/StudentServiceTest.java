package com.example.demo.student;

import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudent() {
        //when
        underTest.getStudent();
        //then
        Mockito.verify(studentRepository).findAll();
    }

    @Test
    void canAddNewStudent() {
        // given
        Student student = new Student(
                "nimal",
                "nimal@gmail.com",
                LocalDate.of(2000, Month.OCTOBER, 5)
        );

        // when
        underTest.addNewStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepository).save(studentArgumentCaptor.capture());

        Student captureStudent = studentArgumentCaptor.getValue();
        Assertions.assertThat(captureStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = new Student(
                "nimal",
                "nimal@gmail.com",
                LocalDate.of(2000, Month.OCTOBER, 5)
        );

        BDDMockito.given(studentRepository.findStudentByEmail(student.getEmail())).willReturn(Optional.of(student));

        // when
        // then
        Assertions.assertThatThrownBy(()->underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email taken");

        Mockito.verify(studentRepository, Mockito.never()).save(ArgumentMatchers.any());

    }

    @Test
    void canDeleteStudent() {
        // given
        long studentId = 1L;
        Student student = new Student(
                studentId,
                "nimal",
                "nimal@gmail.com",
                LocalDate.of(2000, Month.OCTOBER, 5)
        );

        BDDMockito.given(studentRepository.existsById(studentId)).willReturn(true);

        // when
        underTest.deleteStudent(studentId);

        // then

        ArgumentCaptor<Long> studentArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(studentRepository).deleteById(studentArgumentCaptor.capture());

    }

    @Test
    void willThrowStudentIdDoesntExist() {
        // given
        long studentId = 1L;
        Student student = new Student(
                studentId,
                "nimal",
                "nimal@gmail.com",
                LocalDate.of(2000, Month.OCTOBER, 5)
        );

        BDDMockito.given(studentRepository.existsById(studentId)).willReturn(false);

        // when
        // then
        Assertions.assertThatThrownBy(()->underTest.deleteStudent(studentId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student Id "+studentId+" does not exist.");

        Mockito.verify(studentRepository, Mockito.never()).deleteById(ArgumentMatchers.any());

    }

    @Test
    void canUpdateStudent() {
        // given
        Long studentId = 1L;
        String name = "updatedName";
        String email = "updatedEmail@gmail.com";

        Student student = new Student(
                studentId,
                "oldName",
                "oldEmail@gmail.com",
                LocalDate.of(2000, Month.OCTOBER, 5)
        );
        BDDMockito.given(studentRepository.findById(studentId)).willReturn(Optional.of(student));
        BDDMockito.given(studentRepository.findStudentByEmail(email)).willReturn(Optional.empty());

        // when
        underTest.updateStudent(studentId, name, email);

        // then

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(studentRepository).findById(idArgumentCaptor.capture());
        Mockito.verify(studentRepository).findStudentByEmail(emailArgumentCaptor.capture());

        // Assert the captured values

        Long captureStudentId = idArgumentCaptor.getValue();
        Assertions.assertThat(captureStudentId).isEqualTo(studentId);

        String captureStudentEmail = emailArgumentCaptor.getValue();
        Assertions.assertThat(captureStudentEmail).isEqualTo(email);
    }

    @Test
    void willThrowIfNewEmailDoesExist() {
        // given
        Long studentId = 1L;
        String name = "updatedName";
        String email = "updatedEmail@gmail.com";

        Student student = new Student(
                studentId,
                "oldName",
                "oldEmail@gmail.com",
                LocalDate.of(2000, Month.OCTOBER, 5)
        );
        BDDMockito.given(studentRepository.findById(studentId)).willReturn(Optional.of(student));
        BDDMockito.given(studentRepository.findStudentByEmail(email)).willReturn(Optional.of(student));

        // when
        // then
        Assertions.assertThatThrownBy(()->underTest.updateStudent(studentId, name, email))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email taken");

    }
}