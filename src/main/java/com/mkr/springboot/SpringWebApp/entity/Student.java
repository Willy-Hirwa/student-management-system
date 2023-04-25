package com.mkr.springboot.SpringWebApp.entity;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name="learner")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="full_names")
    private String fullNames;

    @Column(name="email")
    private String email;
    private String ImageName;

    private String filename;

    // define constructors

    public Student() {
    }

    public Student(String fullNames, String email) {
        this.fullNames = fullNames;
        this.email = email;
    }

    public Student(String fullNames, String email, String imageName, String filename) {
        this.fullNames = fullNames;
        this.email = email;
        ImageName = imageName;
        this.filename = filename;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFullNames() {
        return fullNames;
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullNames='" + fullNames + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
