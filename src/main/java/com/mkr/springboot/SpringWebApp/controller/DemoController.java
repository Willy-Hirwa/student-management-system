package com.mkr.springboot.SpringWebApp.controller;
import com.mkr.springboot.SpringWebApp.Service.MailService;
import com.mkr.springboot.SpringWebApp.Service.StudentService;
import com.mkr.springboot.SpringWebApp.entity.Student;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/students")
public class DemoController {

    private static String UPLOAD_DIR = "./uploads/";
    private static String IMAGE_DIR = "./images/";
    private StudentService studentService;
    private MailService mailService;
    @Autowired
    public DemoController(StudentService studentService, MailService mailService) {
        this.studentService = studentService;
        this.mailService = mailService;
       }

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    @GetMapping("/admission")
    public String admissionForm(Model theModel){
        // create Model attribute to bind form data
        Student student = new Student();
        theModel.addAttribute("student", student);
        return "Admission.html";
    }

    @PostMapping("/save")
    public String saveStudent(@RequestParam("files")MultipartFile file, @RequestParam("pic")MultipartFile pic,
                              @ModelAttribute("student") Student theStudent) throws IOException {

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String Image = StringUtils.cleanPath(pic.getOriginalFilename());
        Student dbStudent = new Student(theStudent.getFullNames(), theStudent.getEmail());

        // Saving file to the server
        try{
            byte[] bytes = file.getBytes();
            String originalFileName = file.getOriginalFilename();
            String uniqueFileName = originalFileName;
            int i = 1;
            while (Files.exists(Paths.get(UPLOAD_DIR + uniqueFileName))) {
                int dotIndex = originalFileName.lastIndexOf(".");
                if (dotIndex == -1) {
                    uniqueFileName = originalFileName + "_" + i;
                }else {
                    uniqueFileName = originalFileName.substring(0, dotIndex) + "_" + i + originalFileName.substring(dotIndex);
                }
                i++;
            }
            Path path = Paths.get(UPLOAD_DIR + uniqueFileName);
            dbStudent.setFilename(uniqueFileName);
            Files.write(path, bytes);
            logger.info(">>>>>>>>>>>>>>>>>>>>>>Path for file" + path);
        }catch (IOException e){
            e.printStackTrace();
        }

        // Saving Images(pic) to the server
        try{
            byte[] bytes = pic.getBytes();
            String originalPicName = pic.getOriginalFilename();
            String uniquePicName = originalPicName;
            int i = 1;
            while (Files.exists(Paths.get(IMAGE_DIR + uniquePicName))) {
                int dotIndex = originalPicName.lastIndexOf(".");
                if (dotIndex == -1) {
                     uniquePicName = originalPicName + "_" + i;
                }else{
                    uniquePicName = originalPicName.substring(0, dotIndex) + "_" + i + originalPicName.substring(dotIndex);
                }
                i++;
            }
            Path path = Paths.get(IMAGE_DIR + uniquePicName);
            dbStudent.setImageName(uniquePicName);
            logger.info(">>>>>>>>>>>>>>>>>>>>>Path for Image" + path);
            Files.write(path, bytes);
        }catch (IOException e){
            e.printStackTrace();
        }

        // save the employee
        studentService.save(dbStudent);
        mailService.sendMail("studentdody330@gmail.com");
        return "redirect:/students/list";

    }

    @GetMapping("/list")
    public String getAllStudents(Model theModel){

        List<Student> theStudents = studentService.findAll();

        // add to the spring model
        theModel.addAttribute("theStudents", theStudents);

        return "students-list";
    }

    @GetMapping("/view/{filename:.+}")
    public void viewFile(@PathVariable String filename, HttpServletResponse response) throws IOException {
        Path file = Paths.get(UPLOAD_DIR + filename);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>Name of file at download "  + filename);
        if (Files.exists(file)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=" + filename);
            Files.copy(file, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = new FileSystemResource(IMAGE_DIR + filename);
        if (file.exists() && file.isReadable()) {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/ShowUpdateForm")
    public String ShowUpdateForm(@RequestParam("studentId") int id, Model theModel){
        Student student = studentService.findById(id);
        theModel.addAttribute("student", student);
        return "Admission.html";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("studentId") int theId, Model theModel){
        // get the employee from the service
        Student thestudent = studentService.findById(theId);

        // set employee in the model to prepopulate the form
        theModel.addAttribute("student", thestudent);
        logger.info("in update for "+ theId);
        //logger.info(thestudent.toString());

        // send over to our form
        return "Admission";
    }
    @GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam("studentId") int theId){
        studentService.deleteById(theId);
        return "redirect:/students/list";
    }
}
