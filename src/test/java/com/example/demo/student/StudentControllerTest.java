package com.example.demo.student;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class StudentControllerTest {
    /**
     * Method under test: {@link StudentController#getStudent()}
     */
    @Test
    void testGetStudent() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        StudentRepository studentRepository = mock(StudentRepository.class);
        ArrayList<Student> studentList = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(studentList);
        List<Student> actualStudent = (new StudentController(new StudentService(studentRepository))).getStudent();
        assertSame(studentList, actualStudent);
        assertTrue(actualStudent.isEmpty());
        verify(studentRepository).findAll();
    }

    /**
     * Method under test: {@link StudentController#getStudent()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetStudent2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.demo.student.StudentService.getStudent()" because "this.studentService" is null
        //       at com.example.demo.student.StudentController.getStudent(StudentController.java:22)
        //   See https://diff.blue/R013 to resolve this issue.

        (new StudentController(null)).getStudent();
    }

    /**
     * Method under test: {@link StudentController#getStudent()}
     */
    @Test
    void testGetStudent3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        StudentService studentService = mock(StudentService.class);
        ArrayList<Student> studentList = new ArrayList<>();
        when(studentService.getStudent()).thenReturn(studentList);
        List<Student> actualStudent = (new StudentController(studentService)).getStudent();
        assertSame(studentList, actualStudent);
        assertTrue(actualStudent.isEmpty());
        verify(studentService).getStudent();
    }
}

