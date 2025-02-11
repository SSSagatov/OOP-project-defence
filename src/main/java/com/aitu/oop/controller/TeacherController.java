package com.aitu.oop.controller;


import com.aitu.oop.dbconnection.DbConnection;
import com.aitu.oop.entities.Teacher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class TeacherController {

    private final ObjectMapper oMapper;

    @Autowired
    public TeacherController(ObjectMapper objectMapper) {
        this.oMapper = objectMapper;
    }


    @GetMapping("/main/teacher")
    public String myTeacherListener() {
        Teacher teacher = new Teacher(1, "Sanzhar", 18, 5);
        String jsonData = null;
        try {
            jsonData = oMapper.writeValueAsString(teacher);
        } catch (JsonProcessingException e) {
            System.out.println("Error system");
        }

        return jsonData;
    }

    @PostMapping("/main/customTeacher")
    public String myCustomTeacher(@RequestParam int teacher_id,
                                  @RequestParam String teacherName,
                                  @RequestParam int age,
                                  @RequestParam int experience) {
        String jsonData = null;
        Teacher teacher = new Teacher(teacher_id, teacherName, age, experience);
        try {
            jsonData = oMapper.writeValueAsString(teacher);
        } catch (JsonProcessingException e) {
            System.out.println("Error system" + e);
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

    @GetMapping("/main/allTeachers")
    public String myAllTeachers() {
        DbConnection myConnection = new DbConnection();
        Connection con = null;
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        try {
            con = myConnection.connect();
            teachers = myConnection.getTeachers(con);
        } catch (Exception e) {
            System.out.println("Error getting all teachers");
        }


        String jsonData = null;
        try {
            jsonData = oMapper.writeValueAsString(teachers);
        } catch (JsonProcessingException e) {
            System.out.println("Some error with teachers");
        }


        return jsonData;
    }

    @PostMapping("/main/findTeacher")
    public String myFindTeachers(@RequestParam int teacher_id) {
        DbConnection myConnection = new DbConnection();
        Connection con = null;
        Teacher teacher = null;
        try {
            con = myConnection.connect();
            teacher = myConnection.findTeacherById(con, teacher_id);
        } catch (Exception e) {
            System.out.println("Error finding teacher");
        }

        String jsonData = null;
        try {
            jsonData = oMapper.writeValueAsString(teacher);
        } catch (Exception e) {
            System.out.println("Some error with teacher");
        }

        return jsonData;
    }

    @PostMapping("/main/createTeacher")
    public String myCreateTeacher(@RequestParam int teacher_id,
                                  @RequestParam String teacherName,
                                  @RequestParam int age,
                                  @RequestParam int experience) {
        DbConnection myConnection = new DbConnection();
        Teacher teacher1 = new Teacher(teacher_id, teacherName, age, experience);
        String jsonData = null;
        try (Connection con = myConnection.connect()) {
            Teacher createdTeacher = myConnection.createTeacher(con, teacher1);
            jsonData = oMapper.writeValueAsString(createdTeacher);
        } catch (JsonProcessingException e) {
            System.out.println("Error with JSON processing: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating teacher: " + e.getMessage());
        }
        return jsonData;
    }

    @PostMapping("/main/updateTeacher")
    public String updateTeacher(@RequestParam int teacher_id,
                                @RequestParam String newTeacherName,
                                @RequestParam int newAge,
                                @RequestParam int newExperience) {
        DbConnection myConnection = new DbConnection();
        Connection con = null;
        Teacher teacher = null;
        String jsonData = null;
        try {
            con = myConnection.connect();
            teacher = myConnection.findTeacherById(con, teacher_id);
            System.out.println(teacher);
            if (teacher != null) {
                teacher.setTeacherName(newTeacherName);
                teacher.setAge(newAge);
                teacher.setExperience(newExperience);
                System.out.println(teacher);
                con = myConnection.connect();
                myConnection.updateTeacher(con, teacher);
            }
        } catch (Exception e) {
            System.out.println("Teacher Update Error: " + e.getMessage());
        }

        try {
            jsonData = oMapper.writeValueAsString(teacher);
        } catch (JsonProcessingException e) {
            System.out.println("Some error with teacher");
        }

        return jsonData;
    }

    @PostMapping("/main/deleteTeacher")
    public String deleteTeacher(@RequestParam int teacher_id) {
        DbConnection myConnection = new DbConnection();
        Connection con = null;
        Teacher deletedTeacher = null;
        String jsonData = null;
        try {
            con = myConnection.connect();
            deletedTeacher = myConnection.deleteTeacher(con, teacher_id);
        } catch (Exception e) {
            System.out.println("Teacher Delete Error: " + e.getMessage());
        }
        try {
            jsonData = oMapper.writeValueAsString(deletedTeacher);
        } catch (JsonProcessingException e) {
            System.out.println("Some error with delete operation ");
        }
        return jsonData;
    }

}