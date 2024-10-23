package org.Ashwani.Emp_Project.Repository;

import org.Ashwani.Emp_Project.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmailAndPassword(String email,String password);

    boolean existsByEmail(String email);
}
