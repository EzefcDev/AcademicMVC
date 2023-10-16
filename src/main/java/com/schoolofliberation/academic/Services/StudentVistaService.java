package com.schoolofliberation.academic.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolofliberation.academic.Repositories.*;
import com.schoolofliberation.academic.converters.StudentConverter;
import com.schoolofliberation.academic.dto.StudentDTO;
import com.schoolofliberation.academic.entities.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentVistaService {
    
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    StudentConverter studentConverter;

    private static final String STUDENT_NO_EXIST = "No existe el estudiante con id: ";

    public Page<Student> getStudents(Integer page, Integer size, String name, String orientation, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(orientation),orderBy));
        if (!name.isEmpty()) {
            Page<Student> listSearch = studentRepository.findByNameContaining(name, pageable);
            if (!listSearch.isEmpty()) {
                log.info("Devolución de alumnos con parámetro de búsqueda");
                return listSearch;
            } 
            log.error("no se encontraron alumnos, lista vacía");
            return listSearch;
        } 
        Page<Student> listStudents = studentRepository.findAll(pageable);
        log.info("Devolución de alumnos sin parámetro de búsqueda");
        return listStudents;
    }

    public ResponseEntity<String> deleteStudent(Long id){
        Optional<Student> studentExist = studentRepository.findById(id);
        if (!studentExist.isPresent()) {
            log.error(STUDENT_NO_EXIST + id);
            return new ResponseEntity<>("Estudiante no existe con ese id o ya fue eliminado", HttpStatus.BAD_REQUEST);
        }
        studentRepository.deleteById(id);
        log.info("Eliminación del estudiante con id: " + id);
        return new ResponseEntity<>("El estudiante se elimino correctamente", HttpStatus.ACCEPTED);
    }

    public String createStudent(StudentDTO newStudent){
        if (studentRepository.existsByDni(newStudent.getDni())) {
            if (!isEquals(newStudent)) {
                log.error("El estudiante con dni: " + newStudent.getDni() + " ya existe, pero sus campos no son iguales");
                return "Estudiante ya existe pero sus atributos son diferentes, por favor utilizar método actualizar";
            }
            log.error("El estudiante con dni: " + newStudent.getDni() + " ya existe");
            return "Estudiante ya existe";
        }
        studentRepository.save(studentConverter.dtoToEntity(newStudent));
        log.info("El estudiante se creo correctamente");
        return "Estudiante creado";
    }

    public ResponseEntity<String> updateStudent(Long id, StudentDTO studentDTO){
        Optional<Student> studentExist = studentRepository.findById(id);
        if (!studentExist.isPresent()) {
            log.error(STUDENT_NO_EXIST + id);
            return new ResponseEntity<>("El estudiante con id: " + id + " no existe", HttpStatus.BAD_REQUEST);
        }
        Student updateStudent = studentConverter.dtoToEntity(studentDTO);
        updateStudent.setId(id);
        studentRepository.save(updateStudent);
        log.info("Estudiante actualizado correctamente");
        return new ResponseEntity<>("El estudiante se actualizo correctamente", HttpStatus.ACCEPTED);
    }

    public Object getStudent(Long id){
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            log.info("Estudiante esta presente, se muestran sus datos");
            return studentConverter.entityToDTO(student.get());
        }
        log.error(STUDENT_NO_EXIST + id);
        return "El estudiante con id: " + id + " no existe";
    }

    public boolean isEquals(StudentDTO dto){
        StudentDTO studentDTO = studentConverter.entityToDTO(studentRepository.findByDni(dto.getDni()));
        return dto.equals(studentDTO);
    }

    public ResponseEntity<Object> getDeleteStudents(Integer page, Integer size,String orientation, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(orientation),orderBy));
        Page<Student> deleteStudents = studentRepository.findAllDeleteStudents(pageable);
        return new ResponseEntity<>(deleteStudents,HttpStatus.OK );
    }
}
