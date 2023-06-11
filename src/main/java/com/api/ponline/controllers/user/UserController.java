package com.api.ponline.controllers.user;

import com.api.ponline.dao.exception.ResourceNotFoundException;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.security.CurrentUser;
import com.api.ponline.security.UserPrincipal;
import com.api.ponline.services.User.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/details")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    // endpoin untuk mencari data
    // contoh url (base_url/find/id/{idnya})
    @GetMapping("/findUserById")
    public User findOne(@RequestParam Long id) {
        return userService.findOneById(id);
    }

     // enpoind untuk membaca semua data
     @GetMapping
     public Iterable<User> findAll(){
         return userService.findAll();
     }
}
