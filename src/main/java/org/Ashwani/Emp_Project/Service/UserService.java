package org.Ashwani.Emp_Project.Service;

import org.Ashwani.Emp_Project.Entity.UserEntity;

public interface UserService {
    boolean isValidUser(String username, String password);
    boolean addUser(String username, String email, String password);
    UserEntity findByEmailAndPassword(String email, String password);
}
