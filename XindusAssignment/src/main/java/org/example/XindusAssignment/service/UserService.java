package org.example.XindusAssignment.service;

import org.example.XindusAssignment.model.User;
import org.example.XindusAssignment.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepo repo;

    @Autowired
    PasswordEncoder encoder;

    public String addUser(User user) {

        user.setUserPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "User saved";
    }
}
