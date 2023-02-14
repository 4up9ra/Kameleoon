package com.bagrov.KameleoonRESTAPI.controllers;

import com.bagrov.KameleoonRESTAPI.dto.QuoteDTO;
import com.bagrov.KameleoonRESTAPI.models.Quote;
import com.bagrov.KameleoonRESTAPI.models.Vote;
import com.bagrov.KameleoonRESTAPI.services.QuoteService;
import com.bagrov.KameleoonRESTAPI.util.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;
    private final ModelMapper modelMapper;

    @Autowired
    public QuoteController(QuoteService quoteService, ModelMapper modelMapper) {
        this.quoteService = quoteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<QuoteDTO> getQuotes() {
        return quoteService.viewAll().stream().map(user -> convertToQuoteDTO(user)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public QuoteDTO getOne(@PathVariable("id") int id) {
        return convertToQuoteDTO(quoteService.viewOne(id));
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid QuoteDTO quoteDTO,
                                             BindingResult bindingResult)   {
        creationErrorCheck(bindingResult);
        quoteService.save(convertToQuote(quoteDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/random")
    public QuoteDTO getRandomQuote() {
        return convertToQuoteDTO(quoteService.viewRandomQuote());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id)    {
        quoteService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid QuoteDTO quoteDTO,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        creationErrorCheck(bindingResult);
        quoteService.edit(id, convertToQuote(quoteDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<HttpStatus> makeAVote(@RequestBody @Valid Vote vote,
                                             BindingResult bindingResult, @PathVariable("id") int id) {
        creationErrorCheck(bindingResult);
        quoteService.makeAVote(id, vote);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/top10")
    public List<QuoteDTO> top10Quotes() {
        return quoteService.top10Quotes().stream().map(user -> convertToQuoteDTO(user)).collect(Collectors.toList());
    }

    @GetMapping("/worse10")
    public List<QuoteDTO> worse10Quotes() {
        return quoteService.worse10Quotes().stream().map(user -> convertToQuoteDTO(user)).collect(Collectors.toList());
    }

    private void creationErrorCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors())  {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(e -> errorMessage.
                    append(e.getField()).append(" - ").append(e.getDefaultMessage()).append(";"));
            throw new QuoteNotCreatedException(errorMessage.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<QuoteErrorResponse> handleException(QuoteNotFoundException e) {
        QuoteErrorResponse response = new QuoteErrorResponse("Quote with this id wasn't found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<QuoteErrorResponse> handleException(QuoteNotCreatedException e) {
        QuoteErrorResponse response = new QuoteErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Quote convertToQuote(QuoteDTO quoteDTO) {
        return modelMapper.map(quoteDTO, Quote.class);
    }

    private QuoteDTO convertToQuoteDTO(Quote quote) {
        return modelMapper.map(quote, QuoteDTO.class);
    }
}
