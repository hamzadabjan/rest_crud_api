package com.hamza.springboot.training.restcrudapis.rest;


import com.hamza.springboot.training.restcrudapis.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    List<Student> theStudents = new ArrayList<>();

    @PostConstruct
    public void loadData(){
        theStudents = new ArrayList<>();

        theStudents.add(new Student("Hamza","Dabjan"));
        theStudents.add(new Student("Hanan","Shmiea"));
        theStudents.add(new Student("Rayhan","Dabjan"));
    }

    // define endpoint for "/student" - return a list of student
    @GetMapping("/students")
    public List<Student> getStudent(){

        return theStudents;
    }

    @GetMapping("/students/{studentId}")

    public Student getSingleStudent(@PathVariable int studentId){


        if((studentId >= theStudents.size()) || (studentId<0)){
            throw new StudentNotFoundException("student id not found - " + studentId);
        }

        return theStudents.get(studentId);
    }

    // Add an exception handler using @ExceptionHandler

    @ExceptionHandler
    public ResponseEntity <StudentErrorResponse> handelException(StudentNotFoundException exc){

        //create a StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity <StudentErrorResponse> handelException(Exception exc){

        //create a StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}