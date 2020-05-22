package com.stevenkolamkuzhiyil.TicTacToe.controller;

import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.InvalidCredentialException;
import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.UserAlreadyExistsException;
import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.ValidationException;
import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import com.stevenkolamkuzhiyil.TicTacToe.model.form.UserProfileDTO;
import com.stevenkolamkuzhiyil.TicTacToe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/user")
public class ApiUserController {

    private final UserService userService;

    @Autowired
    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("profile/update")
    public ResponseEntity<?> updateUserDetails(@ModelAttribute("user") @Valid UserProfileDTO userProfileDTO, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                User user = userService.updateUserDetails(userProfileDTO);
                return ResponseEntity.ok(UserProfileDTO.userProfileDtoFromUser(user));
            } catch (InvalidCredentialException ex) {
                FieldError fieldError = new FieldError(bindingResult.getObjectName(), "oldPassword", ex.getMessage());
                bindingResult.addError(fieldError);
            } catch (UserAlreadyExistsException ex) {
                FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", ex.getMessage());
                bindingResult.addError(fieldError);
            }
        }
        throw new ValidationException(bindingResult);
    }

    @GetMapping("profile/reset")
    public ResponseEntity<?> cancelProfileUpdate(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(UserProfileDTO.userProfileDtoFromUser(user));
    }

}
