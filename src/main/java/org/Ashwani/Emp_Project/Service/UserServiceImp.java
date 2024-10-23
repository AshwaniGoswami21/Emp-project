package org.Ashwani.Emp_Project.Service;

import org.Ashwani.Emp_Project.Entity.UserEntity;
import org.Ashwani.Emp_Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValidUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password) != null;
    }

    @Override
    public boolean addUser(String username, String email, String password) {

        if(userRepository.findByEmailAndPassword(email,password) != null){
            return false;
        }

        UserEntity user = new UserEntity();
        user.setCompanyName(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserEntity findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
