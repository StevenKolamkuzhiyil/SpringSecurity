package com.stevenkolamkuzhiyil.TicTacToe.model.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public abstract class UserDTO {

    @NotEmpty(message = "Please enter a username")
    @Size(min = 3, max = 25, message = "The username must be between 2 and 25 characters long")
    private String username;
    @NotEmpty(message = "Please enter an email address")
    @Email(message = "Please enter a valid email address")
    private String email;

    protected String confirmPassword;

    public UserDTO() {
    }

    public UserDTO(@NotEmpty @Size(min = 3, max = 25) String username,
                   @NotEmpty @Email String email,
                   String confirmPassword) {
        this.username = username;
        this.email = email;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
