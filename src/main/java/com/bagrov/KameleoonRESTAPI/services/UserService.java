package com.bagrov.KameleoonRESTAPI.services;

import com.bagrov.KameleoonRESTAPI.models.Quote;
import com.bagrov.KameleoonRESTAPI.models.User;
import com.bagrov.KameleoonRESTAPI.repositories.QuoteRepository;
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
    private final QuoteRepository quoteRepository;

    @Autowired
    public UserService(UserRepository userRepository, QuoteRepository quoteRepository) {
        this.userRepository = userRepository;
        this.quoteRepository = quoteRepository;
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

    @Transactional
    public void saveQuote(int id, Quote quote) {
        enrichQuote(quote);
        quote.setUser(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException()));
        quoteRepository.save(quote);
    }

    private void enrichQuote(Quote quote)  {
        LocalDate currentDate = LocalDate.now();
        quote.setDateOfCreation(currentDate);
        quote.setDateOfUpdate(currentDate);
    }
}
