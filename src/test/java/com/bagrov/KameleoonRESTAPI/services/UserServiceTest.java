package com.bagrov.KameleoonRESTAPI.services;

import com.bagrov.KameleoonRESTAPI.models.Quote;
import com.bagrov.KameleoonRESTAPI.models.User;
import com.bagrov.KameleoonRESTAPI.repositories.QuoteRepository;
import com.bagrov.KameleoonRESTAPI.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository, null);
    }

    @Test
    public void testFindAll() {

        //given
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));


        //when
        List<User> users = userService.findAll();

        //then
        assertEquals(2, users.size());
    }

    @Test
    public void testFindAllEmpty() {

        //given
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());


        //when
        List<User> users = userService.findAll();

        //then
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindOne() {

        //given
        int userId = 1;
        User user = new User();
        user.setId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //when
        User result = userService.findOne(userId);

        //then
        assertEquals(userId, result.getId());
    }

    @Test
    public void testSave() {

        //given
        User user = new User();

        //when
        userService.save(user);

        //then
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void testSaveQuote() {

        //given
        int userId = 1;
        Quote quote = new Quote();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        QuoteRepository quoteRepository = Mockito.mock(QuoteRepository.class);

        userService = new UserService(userRepository, quoteRepository);

        //when
        userService.saveQuote(userId, quote);

        //then
        Mockito.verify(quoteRepository).save(quote);
    }
}