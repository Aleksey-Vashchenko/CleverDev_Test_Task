package com.vashchenko.cleverdev_test_task.service;

import com.vashchenko.cleverdev_test_task.entity.User;
import com.vashchenko.cleverdev_test_task.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    public User findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public User save(User newUser) {
        return repository.save(newUser);
    }
}
