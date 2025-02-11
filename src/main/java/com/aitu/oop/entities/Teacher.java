package com.aitu.oop.entities;


public class Teacher extends People{

    private int teacher_id;
    private String teacherName;
    private int experience;
    private int age;


    public Teacher(int teacher_id, String teacherName, int experience, int age) {
        this.teacher_id = teacher_id;
        this.teacherName = teacherName;
        this.experience = experience;
        this.age = age;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
