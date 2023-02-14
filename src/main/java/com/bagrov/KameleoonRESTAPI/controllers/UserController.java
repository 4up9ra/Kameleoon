package com.bagrov.KameleoonRESTAPI.controllers;

import com.bagrov.KameleoonRESTAPI.dto.UserDTO;
import com.bagrov.KameleoonRESTAPI.models.User;
import com.bagrov.KameleoonRESTAPI.services.UserService;
import com.bagrov.KameleoonRESTAPI.util.UserErrorResponse;
import com.bagrov.KameleoonRESTAPI.util.UserNotCreatedException;
import com.bagrov.KameleoonRESTAPI.util.UserNotFoundException;
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
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<UserDTO> getPeople() {
        return userService.findAll().stream().map(user -> convertToUserDTO(user)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getOne(@PathVariable("id") int id) {
        return convertToUserDTO(userService.findOne(id));
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult)   {
        creationErrorCheck(bindingResult);
        userService.save(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void creationErrorCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors())  {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(e -> errorMessage.
                    append(e.getField()).append(" - ").append(e.getDefaultMessage()).append(";"));
            throw new UserNotCreatedException(errorMessage.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse("User with this id wasn't found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {
        UserErrorResponse response = new UserErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
