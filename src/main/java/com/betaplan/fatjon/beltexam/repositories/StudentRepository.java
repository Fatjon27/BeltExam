package com.betaplan.fatjon.beltexam.repositories;

import com.betaplan.fatjon.beltexam.models.Course;
import com.betaplan.fatjon.beltexam.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {
    List<Student> findAll();

//    List<Student> findByCoursesNotContains(Course course);
    //all students that this course currently does not have
    List<Student> findByAllCoursesNotContains(Course course);
}
