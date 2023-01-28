package com.betaplan.fatjon.beltexam.services;

import com.betaplan.fatjon.beltexam.models.LoginUser;
import com.betaplan.fatjon.beltexam.models.User;
import com.betaplan.fatjon.beltexam.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
    public void createUser(User user){
        userRepository.save(user);
    }
    public void updateUser(User user){
        userRepository.save(user);
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //Business logic to register a user
    public User register(User newUser, BindingResult result){
        Optional<User> potentialUser = this.userRepository.findByEmail(newUser.getEmail()) ;
        if(potentialUser.isPresent()) {
            result.rejectValue("email","Exists","This email already exists");
        }
        if(!newUser.getPassword().equals(newUser.getConfirm())){
            result.rejectValue("confirm","Matches","Password and Confirm Password must be the same");
        }
        if(result.hasErrors()){
            return null;
        }
        else {
            String hashed = BCrypt.hashpw(newUser.getPassword(),BCrypt.gensalt());
            newUser.setPassword(hashed);
            return userRepository.save(newUser);
        }
    }

    //Bussines logic to login a existing user
    public User login(LoginUser newLoginObject, BindingResult result){
        Optional<User> potentialUser = this.userRepository.findByEmail(newLoginObject.getEmail());
        if(!potentialUser.isPresent()){
            result.rejectValue("email","Invalid","This email does not exist");
        }
        else {
            if(!BCrypt.checkpw(newLoginObject.getPassword(),potentialUser.get().getPassword())){
                result.rejectValue("password","Incorrect","The password is not correct");
            }
        }
        if(result.hasErrors()){
            return null;
        }
        else {
            return potentialUser.get();
        }
    }
}
