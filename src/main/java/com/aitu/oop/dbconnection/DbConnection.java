package com.aitu.oop.dbconnection;

import com.aitu.oop.entities.Student;
import com.aitu.oop.entities.Teacher;

import java.sql.*;
import java.util.ArrayList;


public class DbConnection {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "sss270906";

    public Connection connect() throws Exception {
        Connection con = DriverManager.getConnection(url, username, password);
        System.out.println("Connected to database");
        return con;
    }

    public int disconnect(Connection con) throws SQLException {
        if (con != null) {
            con.close();
            System.out.println("Disconnected from database");
            return 0;
        }
        System.out.println("No connection to database");
        return 1;
    }


    //Students
    public ArrayList<Student> getStudents(Connection con) throws SQLException {
        String query = "SELECT * FROM public.students";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Student> students = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setStudentName(rs.getString("Student_name"));
            student.setStudent_id(rs.getInt("student_id"));
            student.setScore(rs.getInt("score"));
            student.setAge(rs.getInt("age"));
            students.add(student);
        }
        st.close();
        disconnect(con);
        return students;
    }

    public Student findStudentById(Connection con, int student_id) throws SQLException {
        String query = "SELECT * FROM public.students WHERE student_id = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, student_id);

        ResultSet rs = st.executeQuery();

        ArrayList<Student> students = new ArrayList<>();
        Student student = new Student();
        while (rs.next()) {
            student.setStudentName(rs.getString("Student_name"));
            student.setStudent_id(rs.getInt("student_id"));
            student.setAge(rs.getInt("age"));
            student.setScore(rs.getInt("score"));
            students.add(student);
        }
        st.close();
        disconnect(con);
        return student;
    }

    public Student createStudent(Connection con, Student student) throws SQLException {
        String query = "INSERT INTO public.students (student_id, studentname, age, score) VALUES (?, ?, ?, ?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, student.getStudent_id());
        st.setString(2, student.getStudentName());
        st.setInt(3, student.getScore());
        st.setInt(4, student.getAge());

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Student created successfully");
            return student;
        }
        return null;
    }

    public Student updateStudent(Connection con, Student student) throws SQLException {
        String query = "UPDATE public.students SET studentname=?, age=?, score =? WHERE student_id=?"; // query to be run
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, student.getStudentName());
        st.setInt(2, student.getAge());
        st.setInt(3, student.getStudent_id());
        st.setInt(4, student.getScore());

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Student is updated");
            return student;
        }
        return null;
    }

    public Student deleteStudent(Connection con, int student_id) throws SQLException {
        String query = "DELETE FROM public.students WHERE student_id=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, student_id);

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Student is deleted");
            return new Student(student_id, null, 0, 0);
        }
        return null;
    }


    //Teachers
    public ArrayList<Teacher> getTeachers(Connection con) throws SQLException {
        String query = "SELECT * FROM public.teachers";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Teacher> teachers = new ArrayList<>();
        while (rs.next()) {
            Teacher teacher = new Teacher();
            teacher.setTeacherName(rs.getString("teachername"));
            teacher.setTeacher_id(rs.getInt("teacher_id"));
            teacher.setAge(rs.getInt("age"));
            teacher.setExperience(rs.getInt("experience"));
            teachers.add(teacher);
        }
        st.close();
        disconnect(con);
        return teachers;
    }

    public Teacher findTeacherById(Connection con, int student_id) throws SQLException {
        String query = "SELECT * FROM public.teachers WHERE teacher_id = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, student_id);

        ResultSet rs = st.executeQuery();

        ArrayList<Teacher> teachers = new ArrayList<>();
        Teacher teacher = new Teacher();
        while (rs.next()) {
            teacher.setTeacherName(rs.getString("Teacher_name"));
            teacher.setTeacher_id(rs.getInt("student_id"));
            teacher.setAge(rs.getInt("age"));
            teacher.setExperience(rs.getInt("experience"));
            teachers.add(teacher);
        }
        st.close();
        disconnect(con);
        return teacher;
    }

    public Teacher createTeacher(Connection con, Teacher teacher) throws SQLException {
        String query = "INSERT INTO public.teachers (teacher_id, teachername, age, experience) VALUES (?, ?, ?, ?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, teacher.getTeacher_id());
        st.setString(2, teacher.getTeacherName());
        st.setInt(3, teacher.getAge());
        st.setInt(4, teacher.getExperience());

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Teacher created successfully");
            return teacher;
        }
        return null;
    }

    public Teacher updateTeacher(Connection con, Teacher teacher) throws SQLException {
        String query = "UPDATE public.teachers SET teachername=?, age=?, experience=? WHERE teacher_id=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, teacher.getTeacherName());
        st.setInt(2, teacher.getAge());
        st.setInt(3, teacher.getTeacher_id());
        st.setInt(4, teacher.getExperience());

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Teacher is updated");
            return teacher;
        }
        return null;
    }

    public Teacher deleteTeacher(Connection con, int student_id) throws SQLException {
        String query = "DELETE FROM public.teachers WHERE teacher_id=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, student_id);

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Teacher is deleted");
            return new Teacher(student_id, null, 0, 0);
        }
        return null;
    }
}