package com.betaplan.fatjon.beltexam.repositories;

import com.betaplan.fatjon.beltexam.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course,Long> {
    List<Course> findAll();

}
