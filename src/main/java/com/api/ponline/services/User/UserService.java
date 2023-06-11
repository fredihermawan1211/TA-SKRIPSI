package com.api.ponline.services.User;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ponline.dao.exception.ResourceNotFoundException;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.repository.user.UserRepo;
import com.api.ponline.security.UserPrincipal;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User save(User user) {
        return userRepo.save(user);
    }

    public User findOne(Long id) {
        return userRepo.findById(id).get();
    }

    public Boolean isExist(Long id) {
        User user = findOne(id);
        if (user!=null) {
            return true;
        }else{
            return false;
        }
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public Boolean deleteById(Long id) {
        if (isExist(id)) {
            userRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public User findOneByEmail(String email) {
        return userRepo.findOneByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public List<User> findByTokEmailVerified(String token) {
        return userRepo.findByTokEmailVerified(token);
    }

    public List<User> findByTokResetPassword(String token) {
        return userRepo.findByTokResetPassword(token);
    }

    public User findOneById(Long id) {
        return userRepo.findOneById(id);
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public User getCurrentUser(UserPrincipal userPrincipal) {
        return findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
    
}
