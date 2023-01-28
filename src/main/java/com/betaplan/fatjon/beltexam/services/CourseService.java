package com.betaplan.fatjon.beltexam.services;

import com.betaplan.fatjon.beltexam.models.Course;
import com.betaplan.fatjon.beltexam.repositories.CourseRepository;
import com.betaplan.fatjon.beltexam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Course> findAll(){
        return courseRepository.findAll();
    }
    public void createCourse(Course course){
        courseRepository.save(course);
    }
    public void updateCourse(Course course){
        courseRepository.save(course);
    }
    public Course findById(Long id){
        return courseRepository.findById(id).orElse(null);
    }
    public void deleteById(Long id){
        courseRepository.deleteById(id);
    }
    public void deleteByCourse(Course course){
        courseRepository.delete(course);
    }
}
