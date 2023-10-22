package com.schoolofliberation.academic.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.schoolofliberation.academic.Services.StudentVistaService;
import com.schoolofliberation.academic.dto.StudentDTO;
import com.schoolofliberation.academic.entities.Student;

@Controller
public class StartController {

    @Autowired
    StudentVistaService studentService;
    
    @GetMapping("/")
    public String start(Model model,
        @RequestParam(defaultValue = "0") Integer page, 
        @RequestParam(defaultValue = "10") Integer size, 
        @RequestParam(required = false, name="name", defaultValue = "") String name,
        @RequestParam(defaultValue = "asc") String orientation,
        @RequestParam(defaultValue = "id") String orderBy){
        String location = "Estudiantes";
        model.addAttribute("location", location);
        Page<Student> pageStudents = studentService.getStudents(page, size, name, orientation, orderBy);
        List<Student> students = new ArrayList<>();
        for (Student student : pageStudents.getContent()) {
            Student studentNew = new Student();
            studentNew.setId(student.getId());
            studentNew.setName(student.getName());
            studentNew.setLastname(student.getLastname());
            studentNew.setDni(student.getDni());
            studentNew.setStudentCareer(student.getStudentCareer());
            studentNew.setStudentStatus(student.getStudentStatus());
            students.add(studentNew);
        }
        model.addAttribute("totalElementsForPage", pageStudents.getPageable().getPageSize());
        model.addAttribute("pageLocation", pageStudents.getPageable().getPageNumber());
        model.addAttribute("totalPage", pageStudents.getTotalPages());
        model.addAttribute("totalElements", pageStudents.getTotalElements());
        model.addAttribute("students", students);
        model.addAttribute("name", "");
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        List<Integer> opciones = Arrays.asList(10, 30, 50);
        model.addAttribute("opciones", opciones);
        model.addAttribute("itemsPage", pageStudents);
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(StudentDTO student,Model model){
        String locationAgregar = "Agregar";
        model.addAttribute("locationAgregar", locationAgregar);
        return "crear";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid StudentDTO studentDTO, Errors errors){
        if (errors.hasErrors()) {
            return "crear";
        }
        studentService.createStudent(studentDTO);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(name = "id") Long id ,StudentDTO studentDTO, Model model){
        String locationAgregar = "Editar";
        model.addAttribute("locationAgregar", locationAgregar);
        model.addAttribute("idURL", id);
        Object studentObtenido = studentService.getStudent(id);
        model.addAttribute("studentDTO", studentObtenido);
        return "modificar";
    }

    @PostMapping("/guardar-editado/{id}")
    public String guardarModificado(@PathVariable(name = "id") Long id ,@Valid StudentDTO studentDTO, Errors errors){
        if (errors.hasErrors()) {
            return "modificar";
        }
        studentService.updateStudent(id,studentDTO);
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(name = "id") Long id){
        studentService.deleteStudent(id);
        return "redirect:/";
    }
}
