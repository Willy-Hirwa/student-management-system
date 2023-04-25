package com.mkr.springboot.SpringWebApp.Service;

import com.mkr.springboot.SpringWebApp.dao.StudentRepository;
import com.mkr.springboot.SpringWebApp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements  StudentService{
    private StudentRepository studentRepository;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int theId) {
        Optional<Student> result = studentRepository.findById(theId);

        Student theStudent = null;

        if (result.isPresent()) {
            theStudent = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }

        return theStudent;
    }

    @Override
    public void save(Student theStudent) {

        studentRepository.save(theStudent);
    }

    @Override
    public void deleteById(int theId) {
        studentRepository.deleteById(theId);
    }
}
