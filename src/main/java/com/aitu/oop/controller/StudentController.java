package com.aitu.oop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aitu.oop.dbconnection.DbConnection;
import com.aitu.oop.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class StudentController {

    private final ObjectMapper oMapper;

    @Autowired
    public StudentController(ObjectMapper objectMapper) {
        this.oMapper = objectMapper;
    }


    @GetMapping("/main/Student")
    public String myStudentListener(){
        Student student1 = new Student(1, "Sanzhar", 18, 5);
        String jsonData=null;
        try{
            jsonData=oMapper.writeValueAsString(student1);
        }catch(JsonProcessingException e){
            System.out.println("Error system");
        }

        return jsonData;
    }

    @PostMapping("/main/customStudent")
    public String myCustomStudentListener(@RequestParam int student_id,
                                          @RequestParam String studentName,
                                          @RequestParam int age,
                                          @RequestParam int score) {
        String jsonData = null;
        Student student1 = new Student(student_id, studentName, age, score);
        try {
            jsonData = oMapper.writeValueAsString(student1);
        } catch (JsonProcessingException e) {
            System.out.println("Error system" + e.toString());
        }

        DbConnection con = new DbConnection();
        try {
            con.connect();
        } catch (Exception e) {
            System.out.println("Error with database connection");
            throw new RuntimeException(e);
        }
        return jsonData;
    }

    @GetMapping("/main/allStudents")
    public String myAllStudentsListener(){
        DbConnection myConnection=new DbConnection();
        Connection con=null;
        ArrayList<Student> students=new ArrayList<Student>();
        try{
            con = myConnection.connect();
            students = myConnection.getStudents(con);
        }catch(Exception e){
            System.out.println("Error getting all students");
        }


        String jsonData=null;
        try{
            jsonData=oMapper.writeValueAsString(students);
        }catch (JsonProcessingException e){
            System.out.println("Some error with artist");
        }


        return jsonData;
    }

    @PostMapping("/main/findStudent")
    public String myFindStudentListener(@RequestParam int student_id) {
        DbConnection myConnection=new DbConnection();
        Connection con=null;
        Student student1=null;
        try {
            con = myConnection.connect();
            student1 = myConnection.findStudentById(con, student_id);
        }catch(Exception e){
            System.out.println("Error finding student");
        }

        String jsonData=null;
        try{
            jsonData=oMapper.writeValueAsString(student1);
        }catch (Exception e) {
            System.out.println("Some error with student");
        }

        return jsonData;
    }

    @PostMapping("/main/createStudent")
    public String myCreateStudentListener(@RequestParam int student_id,
                                          @RequestParam String studentName,
                                          @RequestParam int age,
                                          @RequestParam int score) {
        DbConnection myConnection=new DbConnection();
        Student student1 = new Student(student_id, studentName, age, score);
        String jsonData = null;
        try (Connection con = myConnection.connect()) {
            Student createdStudent = myConnection.createStudent(con, student1);
            jsonData = oMapper.writeValueAsString(createdStudent);
        } catch (JsonProcessingException e) {
            System.out.println("Error with JSON processing: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating student: " + e.getMessage());
        }
        return jsonData;
    }

    @PostMapping("/main/updateStudent")
    public String updateStudent(@RequestParam int student_id,
                                @RequestParam String newStudentName,
                                @RequestParam int newAge,
                                @RequestParam int newScore) {
        DbConnection myConnection = new DbConnection();
        Connection con = null;
        Student student = null;
        String jsonData = null;
        try {
            con = myConnection.connect();
            student = myConnection.findStudentById(con, student_id);
            System.out.println(student);
            if (student != null) {
                student.setStudentName(newStudentName);
                student.setAge(newAge);
                student.setScore(newScore);
                System.out.println(student);
                con = myConnection.connect();
                myConnection.updateStudent(con, student);
            }
        } catch (Exception e) {
            System.out.println("Student Update Error: " + e.getMessage());
        }

        try {
            jsonData = oMapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            System.out.println("Some error with student");
        }

        return jsonData;
    }

    @PostMapping("/main/deleteStudent")
    public String deleteStudent(@RequestParam int student_id) {
        DbConnection myConnection = new DbConnection();
        Connection con = null;
        Student deletedStudent = null;
        String jsonData = null;
        try {
            con = myConnection.connect();
            deletedStudent = myConnection.deleteStudent(con, student_id);
        } catch (Exception e) {
            System.out.println("Student Delete Error: " + e.getMessage());
        }
        try {
            jsonData = oMapper.writeValueAsString(deletedStudent);
        } catch (JsonProcessingException e) {
            System.out.println("Some error with delete operation ");
        }
        return jsonData;
    }

}