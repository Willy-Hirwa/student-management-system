package com.mkr.springboot.SpringWebApp.dao;

import com.mkr.springboot.SpringWebApp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    List<Admin> findByEmail(String email);
}
