package com.esprit.spring.ftthback.services;
import com.esprit.spring.ftthback.models.User;
import com.esprit.spring.ftthback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
@Service
public class UserServiceImpl implements Userservice {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> RetreiveAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User Adduser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void DeleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByName(String username) {
        return userRepository.findByUsername(username).get();
    }


    @Override
    public User updateUser(User user, Long id) throws MessagingException {
        user.setId(id);
       User userToUpdate= userRepository.findById(id).get();


        return userRepository.save(user);
    }

    @Override
    public User getById(String id) {
        return userRepository.findUserByUsername(id);

    }


    }

