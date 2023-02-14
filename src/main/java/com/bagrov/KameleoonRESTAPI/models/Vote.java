package com.bagrov.KameleoonRESTAPI.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isUpvote;

    private LocalDateTime votedAt;

    @ManyToOne
    @JoinColumn(name = "quote_id", referencedColumnName = "id")
    private Quote quote;

    public Vote() {
    }

    public Vote(boolean isUpvote, Quote quote) {
        this.isUpvote = isUpvote;
        this.quote = quote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUpvote() {
        return isUpvote;
    }

    public void setUpvote(boolean upvote) {
        isUpvote = upvote;
    }

    public LocalDateTime getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(LocalDateTime votedAt) {
        this.votedAt = votedAt;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
