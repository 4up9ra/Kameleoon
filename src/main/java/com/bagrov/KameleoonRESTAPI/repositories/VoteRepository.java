package com.bagrov.KameleoonRESTAPI.repositories;

import com.bagrov.KameleoonRESTAPI.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
