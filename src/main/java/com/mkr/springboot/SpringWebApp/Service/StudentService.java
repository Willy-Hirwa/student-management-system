package com.mkr.springboot.SpringWebApp.Service;

import com.mkr.springboot.SpringWebApp.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student findById(int theId);

    void save(Student theStudent);

    void deleteById(int theId);
}
