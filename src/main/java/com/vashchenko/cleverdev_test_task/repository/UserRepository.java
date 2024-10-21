package com.vashchenko.cleverdev_test_task.repository;

import com.vashchenko.cleverdev_test_task.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByLogin(String login);
}
