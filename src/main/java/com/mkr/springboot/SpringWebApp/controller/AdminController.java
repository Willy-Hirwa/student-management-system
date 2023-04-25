package com.mkr.springboot.SpringWebApp.controller;

import com.mkr.springboot.SpringWebApp.dao.AdminRepository;
import com.mkr.springboot.SpringWebApp.entity.Admin;
import com.mkr.springboot.SpringWebApp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {
    private AdminRepository adminRepository;
    @Autowired
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping("/SignUp")
    public String signUp(Model theModel){
        // create Model attribute to bind form data
        Admin admin = new Admin();
        theModel.addAttribute("admin", admin);
        return "AdminSignUp.html";
    }

    @GetMapping("/login")
    public String login(Model theModel){
        // create Model attribute to bind form data
        Admin admin = new Admin();
        theModel.addAttribute("admin", admin);
        return "login.html";
    }

    @PostMapping("/save")
    public String saveAdmin(@ModelAttribute("admin") Admin theAdmin){
        // save the Admin
        adminRepository.save(theAdmin);
        return "redirect:/admins/login";
    }

    @PostMapping("/authenticate")
  //  @ResponseBody
    public String AuthenticateAdmin(@ModelAttribute("admin") Admin theAdmin){
        // Find the Admin
        List<Admin> authAdmin = adminRepository.findByEmail(theAdmin.getEmail());
        if(!authAdmin.isEmpty()){
            return "redirect:/students/admission";
        }
        return "redirect:/admins/SignUp";
    }
}
