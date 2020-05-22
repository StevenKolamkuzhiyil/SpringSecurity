package com.stevenkolamkuzhiyil.TicTacToe.model.form;

import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import com.stevenkolamkuzhiyil.TicTacToe.security.validator.FieldsMatch;
import com.stevenkolamkuzhiyil.TicTacToe.security.validator.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldsMatch(field = "confirmPassword", target = "password", message = "Passwords must match")
public class UserProfileDTO extends UserDTO {

    @ValidPassword(nullable = true)
    private String password;
    @NotEmpty(message = "Please enter your password to confirm changes")
    private String currentPassword;

    private int usernameNr;

    public UserProfileDTO() {
    }

    public UserProfileDTO(@NotEmpty @Size(min = 2, max = 25) String username,
                          @NotEmpty @Email String email,
                          String password, String confirmPassword,
                          @NotEmpty String currentPassword,
                          int usernameNr) {
        super(username, email, confirmPassword);
        this.password = password;
        this.currentPassword = currentPassword;
        this.usernameNr = usernameNr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public int getUsernameNr() {
        return usernameNr;
    }

    public void setUsernameNr(int usernameNr) {
        this.usernameNr = usernameNr;
    }

    public static UserProfileDTO userProfileDtoFromUser(User user) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername(user.getUsername());
        userProfileDTO.setUsernameNr(user.getUsernameNr());
        userProfileDTO.setEmail(user.getEmail());
        return userProfileDTO;
    }
}
