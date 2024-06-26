package com.esprit.spring.ftthback.controller;
import com.esprit.spring.ftthback.models.User;
import com.esprit.spring.ftthback.repository.UserRepository;
import com.esprit.spring.ftthback.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @GetMapping("/retrieve-all-user")
    @ResponseBody
    public List<User> getAllUsers() {
        List<User> list = userService.RetreiveAllUser();
        return list;
    }
    @DeleteMapping("/remove-user/{user-id}")
    @ResponseBody
    public void removeUser(@PathVariable("user-id") Long id) {
        userService.DeleteUser(id);

    }
    @GetMapping("/retrieve-user/{user-id}")
    @ResponseBody
    public User getUserById(@PathVariable("user-id")Long id){
        return userService.findById(id);
    }

    @PostMapping("/Add-user")
    @ResponseBody
    public User addUser(@Valid @RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);

        //return userService.Adduser(user);
    }




    @GetMapping("/find-by-username/{username}")
    @ResponseBody
    public User findByname(@PathVariable("username")String username){
        return userService.findByName(username);
    }
    @PutMapping(value="/modifyuser/{user-id}")
    public User modify(@PathVariable (name="user-id") Long id, @RequestBody User user) throws MessagingException {
        return userService.updateUser(user, id);
    }




    @GetMapping("/retrieve-username/{id}")
    @ResponseBody
    public User getusername(@PathVariable("id") String username) {

        return userService.getById(username);
    }


}
