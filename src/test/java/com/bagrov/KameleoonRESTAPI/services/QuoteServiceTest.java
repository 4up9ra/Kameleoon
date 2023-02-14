package com.bagrov.KameleoonRESTAPI.services;

import com.bagrov.KameleoonRESTAPI.models.Quote;
import com.bagrov.KameleoonRESTAPI.models.User;
import com.bagrov.KameleoonRESTAPI.repositories.QuoteRepository;
import com.bagrov.KameleoonRESTAPI.repositories.UserRepository;
import com.bagrov.KameleoonRESTAPI.repositories.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class QuoteServiceTest {

    private QuoteRepository quoteRepository;
    private UserRepository userRepository;
    private VoteRepository voteRepository;
    private QuoteService quoteService;

    @BeforeEach
    public void init() {
        quoteRepository = Mockito.mock(QuoteRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        voteRepository = Mockito.mock(VoteRepository.class);
        quoteService = new QuoteService(quoteRepository, null, null);
    }

    @Test
    public void testViewAll() {

        //given
        List<Quote> quotes = new ArrayList<>();
        Mockito.when(quoteRepository.findAll()).thenReturn(quotes);

        //when
        List<Quote> result = quoteService.viewAll();

        //then
        Mockito.verify(quoteRepository, times(1)).findAll();
        assertEquals(quotes, result);
    }

    @Test
    public void testViewAllReturnsEmptyList() {

        //given
        Mockito.when(quoteRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        List<Quote> result = quoteService.viewAll();

        //then
        Mockito.verify(quoteRepository, times(1)).findAll();
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testViewOne() {

        //given
        int id = 1;
        Quote expectedQuote = new Quote();
        expectedQuote.setId(id);
        expectedQuote.setContent("Test quote");

        Mockito.when(quoteRepository.findById(id)).thenReturn(Optional.of(expectedQuote));

        //when
        Quote result = quoteService.viewOne(id);

        //then
        Mockito.verify(quoteRepository, times(1)).findById(id);
        assertEquals(expectedQuote, result);
    }

    @Test
    public void testViewAllReturnsNonEmptyList() {

        //given
        Quote quote1 = new Quote("1", new User());
        Quote quote2 = new Quote("2", new User());
        List<Quote> quotes = Arrays.asList(quote1, quote2);
        Mockito.when(quoteRepository.findAll()).thenReturn(quotes);

        //when
        List<Quote> result = quoteService.viewAll();

        //then
        assertEquals(result.size(), 2);
        assertTrue(result.containsAll(quotes));
    }

    @Test
    public void testViewAllReturnsEmptyListVersion2() {

        //given
        Mockito.when(quoteRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        List<Quote> result = quoteService.viewAll();

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteQuote() {

        //given
        quoteService = new QuoteService(quoteRepository, voteRepository, userRepository);
        Mockito.when(quoteRepository.existsById(1)).thenReturn(true);

        //when
        quoteService.delete(1);

        //then
        Mockito.verify(quoteRepository, times(1)).deleteById(1);
    }
}