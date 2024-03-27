package com.esprit.spring.ftthback.services;


import com.esprit.spring.ftthback.models.User;

import javax.mail.MessagingException;
import java.util.List;

public interface Userservice {
    List<User> RetreiveAllUser ();
    User Adduser (User user);
    public void DeleteUser(Long id);
    User findById (Long id);
    User findByName(String username);
    User updateUser(User user ,Long id) throws MessagingException;
    User getById(String id);


}
