package com.aitu.oop.entities;


public class Student{

    private int student_id;
    private String studentName;
    private int score;
    private int age;


    public Student(int student_id, String studentName, int score, int age) {
        this.student_id = student_id;
        this.studentName = studentName;
        this.score = score;
        this.age = age;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}