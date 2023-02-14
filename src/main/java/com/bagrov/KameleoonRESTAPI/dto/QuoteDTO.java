package com.bagrov.KameleoonRESTAPI.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class QuoteDTO {

    @NotEmpty(message = "Quote should not be empty")
    private String content;

    private LocalDate dateOfCreation;

    private LocalDate dateOfUpdate;

    private int upVotes;

    private int downVotes;

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
}
