package com.betaplan.fatjon.beltexam.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name must be provided.")
    @Size(min=3,max=30,message = "Name must be at least 3 characters.")
    private String name;
    @NotBlank(message = "Day of the week must be provided.")
    @Size(min=3,max=30,message = "Day of the week must be at least 3 characters.")
    private String day;

    @Positive(message = "price should be higher than 0")
    @Max(value = 250,message = "The price should be lower than 250$")
    private Integer price;
    @NotBlank(message = "Description must be provided.")
    @Size(max=255)
    private String description;

    @NotBlank(message = "Time should be filled")
    private String time;
    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private User instructor;
    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Student> students;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "courses_students",
               joinColumns = @JoinColumn(name = "course_id"),
               inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> allStudents;
    public Course(){
    }
    public Course(String name,String day,Integer price,String description,String time){
        this.name=name;
        this.day=day;
        this.price=price;
        this.description=description;
        this.time=time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public void setAllStudents(List<Student> allStudents) {
        this.allStudents = allStudents;
    }
}
