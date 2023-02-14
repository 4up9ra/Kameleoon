package com.bagrov.KameleoonRESTAPI.repositories;

import com.bagrov.KameleoonRESTAPI.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findByQuoteIdOrderByVotedAtAsc(int quoteId);
}
