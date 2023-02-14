package com.bagrov.KameleoonRESTAPI.services;

import com.bagrov.KameleoonRESTAPI.models.Quote;
import com.bagrov.KameleoonRESTAPI.models.Vote;
import com.bagrov.KameleoonRESTAPI.repositories.QuoteRepository;
import com.bagrov.KameleoonRESTAPI.repositories.UserRepository;
import com.bagrov.KameleoonRESTAPI.repositories.VoteRepository;
import com.bagrov.KameleoonRESTAPI.util.QuoteNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
        long count = quoteRepository.count();
        int randomIndex = (int) (Math.random() * count);
        return entityManager.createQuery("SELECT q FROM Quote q ORDER BY RANDOM()", Quote.class)
                .setFirstResult(randomIndex)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Transactional
    public void edit(int id, Quote quote)   {
        if (!quoteRepository.existsById(id))  {
            throw new QuoteNotFoundException();
        }
        quoteRepository.findById(id).orElseThrow(QuoteNotFoundException::new).setContent(quote.getContent());
        quoteRepository.findById(id).orElseThrow(QuoteNotFoundException::new).setDateOfUpdate(LocalDate.now());
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

        Quote currentQuote = quoteRepository.findById(id).orElseThrow(QuoteNotFoundException::new);

        vote.setVotedAt(LocalDateTime.now());
        vote.setQuote(currentQuote);

        if (vote.isUpvote())    {
            currentQuote.setUpVotes(currentQuote.getUpVotes() + 1);
        } else currentQuote.setDownVotes(currentQuote.getDownVotes() + 1);

        voteRepository.save(vote);
    }

    public List<Vote> viewVotes(int id) {
        if (!quoteRepository.existsById(id))  {
            throw new QuoteNotFoundException();
        }
        return voteRepository.findByQuoteIdOrderByVotedAtAsc(id);
    }

    public List<Quote> top10Quotes()    {
        int size = Math.min(quoteRepository.findAll().size(), 10);
        return quoteRepository.findAll(Sort.by("upVotes").descending()).subList(0, size);
    }

    public List<Quote> worse10Quotes()    {
        int size = Math.min(quoteRepository.findAll().size(), 10);
        return quoteRepository.findAll(Sort.by("downVotes").descending()).subList(0, size);
    }
}
