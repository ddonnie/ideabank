package com.dataart.fastforward.app.dto;

/**
 * Created by Orlov on 26.11.2016.
 */
public class NewAccountDTO {

    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public NewAccountDTO() {
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
