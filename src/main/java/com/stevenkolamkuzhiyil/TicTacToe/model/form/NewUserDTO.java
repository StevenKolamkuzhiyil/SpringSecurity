package com.stevenkolamkuzhiyil.TicTacToe.model.form;

import com.stevenkolamkuzhiyil.TicTacToe.security.validator.FieldsMatch;
import com.stevenkolamkuzhiyil.TicTacToe.security.validator.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldsMatch(field = "confirmPassword", target = "password", message = "Passwords must match")
public class NewUserDTO extends UserDTO {

    @ValidPassword
    private String password;

    public NewUserDTO() {
    }

    public NewUserDTO(@NotEmpty @Size(min = 3, max = 25) String username,
                      @NotEmpty @Email String email,
                      String password,
                      @NotEmpty(message = "Passwords must match") String confirmPassword) {
        super(username, email, confirmPassword);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
