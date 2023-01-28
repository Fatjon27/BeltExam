package com.betaplan.fatjon.beltexam.services;

import com.betaplan.fatjon.beltexam.models.Course;
import com.betaplan.fatjon.beltexam.models.Student;
import com.betaplan.fatjon.beltexam.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public void createStudent(Student student){
        studentRepository.save(student);
    }
    public void updateStudent(Student student){
        studentRepository.save(student);
    }
    public Student findById(Long id){
        return studentRepository.findById(id).orElse(null);
    }
    public void deleteById(Long id){
        studentRepository.deleteById(id);
    }
    public void deleteByStudent(Student student){
        studentRepository.delete(student);
    }
    public List<Student> findAll(){
        return studentRepository.findAll();
    }
    public List<Student> findAllStudentsNotInThisCourse(Course course){
//        return studentRepository.findByCoursesNotContains(course);
        return studentRepository.findByAllCoursesNotContains(course);
    }

 }
