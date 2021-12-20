package com.gakd.handson.controller;

import com.gakd.handson.model.Student;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final List<Student> STUDENTS = Arrays.asList(
            Student.builder().studentId(1).studentName("Tommy").build(),
            Student.builder().studentId(2).studentName("John").build(),
            Student.builder().studentId(3).studentName("Rocky").build()
    );

    @GetMapping(path = "/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){

        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student "+ studentId +" doesn't exist"));
    }
}
