package com.bagrov.KameleoonRESTAPI.services;

import com.bagrov.KameleoonRESTAPI.models.Quote;
import com.bagrov.KameleoonRESTAPI.models.Vote;
import com.bagrov.KameleoonRESTAPI.repositories.QuoteRepository;
import com.bagrov.KameleoonRESTAPI.repositories.UserRepository;
import com.bagrov.KameleoonRESTAPI.repositories.VoteRepository;
import com.bagrov.KameleoonRESTAPI.util.QuoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository, VoteRepository voteRepository, UserRepository userRepository) {
        this.quoteRepository = quoteRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    public List<Quote> viewAll()   {
        return quoteRepository.findAll();
    }

    public Quote viewOne(int id) {
        return quoteRepository.findById(id).orElseThrow(QuoteNotFoundException::new);
    }

    public Quote viewRandomQuote() {
        Random random = new Random();
        int size = quoteRepository.findAll().size();
        return quoteRepository.findById(1 + random.nextInt(size)).orElse(null);
    }

    @Transactional
    public void save(Quote quote) {
        enrichQuote(quote);
        quoteRepository.save(quote);
    }

    @Transactional
    public void edit(int id, Quote quote)   {
        if (!quoteRepository.existsById(id))  {
            throw new QuoteNotFoundException();
        }
        quote.setId(id);
        quote.setDateOfUpdate(LocalDate.now());
        quoteRepository.save(quote);
    }

    @Transactional
    public void delete(int id)   {
        if (!quoteRepository.existsById(id))  {
            throw new QuoteNotFoundException();
        }
        quoteRepository.deleteById(id);
    }

    @Transactional
    public void makeAVote(int id, Vote vote)   {

        if (!quoteRepository.existsById(id))  {
            throw new QuoteNotFoundException();
        }

        Quote currentQuote = quoteRepository.findById(id).orElseThrow();

        vote.setVotedAt(LocalDateTime.now());
        vote.setQuote(currentQuote);

        if (vote.isUpvote())    {
            currentQuote.setUpVotes(currentQuote.getUpVotes() + 1);
        } else currentQuote.setDownVotes(currentQuote.getDownVotes() + 1);

        voteRepository.save(vote);
    }

    public List<Quote> top10Quotes()    {
        int size = Math.min(quoteRepository.findAll().size(), 10);
        return quoteRepository.findAll(Sort.by("upVotes").descending()).subList(0, size);
    }

    public List<Quote> worse10Quotes()    {
        int size = Math.min(quoteRepository.findAll().size(), 10);
        return quoteRepository.findAll(Sort.by("downVotes").descending()).subList(0, size);
    }

    private void enrichQuote(Quote quote)  {
        LocalDate currentDate = LocalDate.now();
        quote.setDateOfCreation(currentDate);
        quote.setDateOfUpdate(currentDate);
    }

}
