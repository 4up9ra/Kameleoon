package com.bagrov.KameleoonRESTAPI.dto;

import com.bagrov.KameleoonRESTAPI.models.Quote;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class UserDTO {

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    private List<Quote> quotes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
