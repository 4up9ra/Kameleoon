package com.bagrov.KameleoonRESTAPI.services;

import com.bagrov.KameleoonRESTAPI.models.User;
import com.bagrov.KameleoonRESTAPI.repositories.UserRepository;
import com.bagrov.KameleoonRESTAPI.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll()   {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void save(User user) {
        enrichUser(user);
        userRepository.save(user);
    }

    private void enrichUser(User user)  {
        user.setDateOfCreation(LocalDate.now());
    }
}
