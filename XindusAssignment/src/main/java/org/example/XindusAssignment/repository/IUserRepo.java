package org.example.XindusAssignment.repository;

import org.example.XindusAssignment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {
    User findByUserEmail(String UserEmail);
    Optional<User> getByuserEmail(String username);

}
