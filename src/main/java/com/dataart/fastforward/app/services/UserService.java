package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.NewUserDTO;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.model.Idea;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
public interface UserService {

    void createUser(NewUserDTO newUserDTO);
    User edit(User user);
    void delete(long userId);

    User getUserById(long userId);
    User getUserByUsername(String username);
    List<User> getAll();

    void addBookmark(Idea idea, User user);
    void deleteBookmark(Idea idea, User user);
    List<Idea> getBookmarks(User user);
}
