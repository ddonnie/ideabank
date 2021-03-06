package com.dataart.fastforward.app.dto;

import com.dataart.fastforward.app.model.Idea;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by logariett on 01.12.2016.
 */
public class UserInfoDTO {

/*    private String firstName;
    private String lastName;*/
    private String username;
    private String role;
    private int bookmarksCount;

    public UserInfoDTO() {
    }

/*    public String getFirstName() {
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
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getBookmarksCount() {
        return bookmarksCount;
    }

    public void setBookmarksCount(int bookmarksCount) {
        this.bookmarksCount = bookmarksCount;
    }
}
