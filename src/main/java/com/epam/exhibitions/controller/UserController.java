package com.epam.exhibitions.controller;

import com.epam.exhibitions.entity.User;
import com.epam.exhibitions.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.Validate;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value ="/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable(name="userId")Long id) {
        Validate.isTrue(id != null, "Id should not be null");
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) throws Exception {
        userService.addUser(user);
        log.info("User " + user.getNickname() + " saved successfully!");
        return ResponseEntity.ok("User is valid");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable(name="userId")Long id){
        // TODO: implement delete method within purchase
        userService.deleteUserById(id);
        log.info("User with id: " + id + " deleted successfully!");
        return ResponseEntity.ok("User has been deleted");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserById(@PathVariable(name="userId")Long id,
                                                 @Valid @RequestBody User user) throws Exception {
        Validate.isTrue(id != null, "Id should not be null");
        User usr = userService.getUserById(id);
        if(usr != null){
            usr.setFirstName(user.getFirstName());
            usr.setLastName(user.getLastName());
            usr.setPassword(user.getPassword());
            usr.setRole(user.getRole());
            userService.updateUser(usr);
        }
        log.info("User " + user.getNickname() + " updated successfully!");
        return ResponseEntity.ok("User is valid");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
