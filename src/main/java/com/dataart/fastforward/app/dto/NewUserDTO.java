package com.dataart.fastforward.app.dto;

import com.dataart.fastforward.app.validation.Alphabetic;
import com.dataart.fastforward.app.validation.NotWhitespace;
import com.dataart.fastforward.app.validation.Password;
import com.dataart.fastforward.app.validation.Username;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;

/**
 * Created by Orlov on 26.11.2016.
 */
public class NewUserDTO {

    @NotNull(message = "error.firstName.notnull")
    @NotWhitespace
    @Alphabetic
    @Size(min = 1, max = 30, message = "error.firstName.size")
    private String firstName;

    @NotNull(message = "error.lastName.notnull")
    @NotWhitespace
    @Alphabetic
    @Size(min = 1, max = 30, message = "error.lastName.size")
    private String lastName;

    @NotNull(message = "error.username.notnull")
    @Username
    @Size(min = 1, max = 30, message = "error.username.size")
    private String username;

    @NotNull(message = "error.password.notnull")
    @Password
    @Size(min = 4, max = 50, message = "error.password.size")
    private String password;

    public NewUserDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @AssertTrue(message = "{error.validation.username.forbidden}")
    private boolean isValid() {
        return !"admin".equals(username)
                && !"loggedUser".equals(username);
    }
}
