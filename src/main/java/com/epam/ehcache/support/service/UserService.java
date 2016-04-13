package com.epam.ehcache.support.service;

import com.epam.ehcache.support.domain.User;
import com.epam.ehcache.support.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Date: 04/13/2016
 *
 * @author Andrei_Khadziukou
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String firstName, String lastName) {
        User user = new User(UUID.randomUUID().toString());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.insert(user);
        return user;
    }

    public List<User> findUsers() {
        return userRepository.find();
    }

    public User find(String id) {
        return userRepository.find(id);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void delete(String id) {
        userRepository.delete(id);
    }
}
