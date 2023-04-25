package com.mkr.springboot.SpringWebApp.dao;

import com.mkr.springboot.SpringWebApp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
