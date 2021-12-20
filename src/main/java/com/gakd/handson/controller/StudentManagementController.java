package com.gakd.handson.controller;

import com.gakd.handson.model.Student;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private final List<Student> STUDENTS = Arrays.asList(
            Student.builder().studentId(1).studentName("Tommy").build(),
            Student.builder().studentId(2).studentName("John").build(),
            Student.builder().studentId(3).studentName("Rocky").build()
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<Student> getAllStudents(){
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('student:write')")
    public void registerStudent(@RequestBody Student student){
        System.out.println(student + "registered");
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAnyAuthority('student:write')")
    public void deleteStudenet(Integer studentId){
        System.out.println(studentId + "deleted");
    }

    @PutMapping(path = "{studentId}")
    public void updateStudenet(@PathVariable("studentId") Integer studentId, Student student){
        System.out.println(studentId + "updated");
    }

}
