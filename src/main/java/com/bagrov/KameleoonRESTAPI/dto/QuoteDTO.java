package com.bagrov.KameleoonRESTAPI.dto;

import com.bagrov.KameleoonRESTAPI.models.User;
import com.bagrov.KameleoonRESTAPI.models.Vote;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public class QuoteDTO {

    @NotEmpty(message = "Quote should not be empty")
    private String content;

    private LocalDate dateOfCreation;

    private LocalDate dateOfUpdate;

    private int upVotes;

    private int downVotes;

    private User user;

    private List<Vote> votes;

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDate getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(LocalDate dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
