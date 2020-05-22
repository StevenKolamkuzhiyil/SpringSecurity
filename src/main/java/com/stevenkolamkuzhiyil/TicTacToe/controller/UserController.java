package com.stevenkolamkuzhiyil.TicTacToe.controller;

import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.UserAlreadyExistsException;
import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import com.stevenkolamkuzhiyil.TicTacToe.model.form.NewUserDTO;
import com.stevenkolamkuzhiyil.TicTacToe.model.form.UserProfileDTO;
import com.stevenkolamkuzhiyil.TicTacToe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new NewUserDTO());
        return modelAndView;
    }

    @PostMapping("register")
    public String registerUserAccount(@ModelAttribute("user") @Valid NewUserDTO newUserDTO, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                userService.registerNewUserAccount(newUserDTO);
                return "redirect:/user/login?create";
            } catch (UserAlreadyExistsException ex) {
                FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", ex.getMessage());
                bindingResult.addError(fieldError);
            }
        }
        return "register";
    }

    @GetMapping(path = {"profile", "profile/index"})
    public ModelAndView profile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("profile/index");
        modelAndView.addObject("user", new UserProfileDTO());
        User user = userService.getUserByEmail(principal.getName());
        modelAndView.addObject("userProperties", UserProfileDTO.userProfileDtoFromUser(user));
        return modelAndView;
    }

}
