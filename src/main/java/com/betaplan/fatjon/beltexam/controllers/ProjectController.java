package com.betaplan.fatjon.beltexam.controllers;

import com.betaplan.fatjon.beltexam.models.Course;
import com.betaplan.fatjon.beltexam.models.LoginUser;
import com.betaplan.fatjon.beltexam.models.Student;
import com.betaplan.fatjon.beltexam.models.User;
import com.betaplan.fatjon.beltexam.services.CourseService;
import com.betaplan.fatjon.beltexam.services.StudentService;
import com.betaplan.fatjon.beltexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ProjectController {
    //Dependency Injections for Services
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    //Main Page when the Project Opens
    @GetMapping("/")
    public String index(Model model, @ModelAttribute("newUser") User newUser, @ModelAttribute("newLogin") LoginUser newLogin) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index";
    }

    //Post Mapping for registering the user
    @PostMapping("/register")
    public String register(Model model, HttpSession session, @Valid @ModelAttribute("newUser") User newUser, BindingResult result) {
        userService.register(newUser, result);
        if (result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index";
        } else {
            session.setAttribute("loggedInUserId", newUser.getId());
            return "redirect:/dashboard";
        }
    }

    //Post Mapping for login-ing the user
    @PostMapping("/login")
    public String login(Model model, HttpSession session, @Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result) {
        User user = this.userService.login(newLogin, result);
        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index";
        } else {
            session.setAttribute("loggedInUserId", user.getId());
            return "redirect:/dashboard";
        }
    }

    //GetMapping to logout the current user using session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //Main Page that shows up when a user registers,login
    //A list of every Course appears
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/";
        } else {
            model.addAttribute("user", userService.findById(loggedInUserId));
            model.addAttribute("courses", courseService.findAll());
            return "dashboard";
        }
    }

    //GetMapping for a new Course,will show createCourse.jsp
    @GetMapping("/classes/new")
    public String createC(Model model, HttpSession session, @ModelAttribute("course") Course course) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/";
        } else {
            User user = userService.findById(loggedInUserId);
            model.addAttribute("user", user);
            return "createCourse";
        }
    }

    //PostMapping to create a new Course
    @PostMapping("/classes/new")
    public String createCourse(Model model, HttpSession session, @Valid @ModelAttribute("course") Course course, BindingResult result) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            return "createCourse";
        } else {
            User user = userService.findById(loggedInUserId);
            course.setInstructor(user);
            courseService.createCourse(course);
            return "redirect:/dashboard";
        }
    }

    //Showind details about a project including all students and available students
    @GetMapping("/classes/{id}")
    public String courseDetails(Model model, @PathVariable("id") Long id,@ModelAttribute("student")Student student) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        model.addAttribute("students",course.getAllStudents());
        model.addAttribute("notYourStudents",studentService.findAllStudentsNotInThisCourse(course));
        return "courseDetails";
    }

    //Post Mapping to create a new Student and assign him to a course
    @PostMapping("/students/{id}/new")
    public String createStudent(Model model,@PathVariable("id") Long id,HttpSession session,@Valid @ModelAttribute("student") Student student,BindingResult result) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        Student newStudent = new Student(student.getEmail(), student.getName());
        if (loggedInUserId == null) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            return "courseDetails";
        } else {
            User user = userService.findById(loggedInUserId);
            Course course = courseService.findById(id);
            newStudent.setCourse(course);
            studentService.createStudent(newStudent);
            course.getAllStudents().add(newStudent);
            courseService.updateCourse(course);
            return "redirect:/classes/"+id;
        }
    }
    //PostMapping to assign a current student to the course that is opened
    @PostMapping("/assign/{id}")
    public String assignStudent(@PathVariable("id") Long courseId,@RequestParam("studentId") Long studentId,HttpSession session){
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/";
        } else{
            Course course = courseService.findById(courseId);
            Student student = studentService.findById(studentId);
            course.getAllStudents().add(student);
            course.getStudents().add(student);
            courseService.updateCourse(course);
            return "redirect:/classes/"+courseId;
        }
    }

    //Edit a course
    @GetMapping("/classes/{id}/edit")
    public String editCourse(Model model, @PathVariable("id") Long id) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        return "editCourse";
    }

    //PutMapping to edit a course
    @PutMapping("/classes/{id}/edit")
    public String updateCourse(HttpSession session,@PathVariable("id") Long id, Model model, @Valid @ModelAttribute("course") Course course, BindingResult result) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/";
        } else {
            if (result.hasErrors()) {
                model.addAttribute("course",courseService.findById(id));
                return "editCourse";
            } else {
                User loggedInUser = userService.findById(loggedInUserId);
                course.setInstructor(loggedInUser);
                courseService.updateCourse(course);
                return "redirect:/dashboard";
            }
        }
    }

    //DeleteMapping to delete a course
    @DeleteMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable("id") Long id,HttpSession session){
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/";
        } else {
            Course course = courseService.findById(id);
            for(Student student: course.getAllStudents()){
                student.getAllCourses().remove(course);
                studentService.updateStudent(student);
            }
            courseService.deleteByCourse(course);
            return "redirect:/dashboard";
        }
    }

}