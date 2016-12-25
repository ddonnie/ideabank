package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dao.UserRepository;
import com.dataart.fastforward.app.dto.NewUserDTO;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.UserService;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dataart.fastforward.app.services.validation.ValidationUtils.assertNotBlank;

/**
 * Created by logariett on 19.11.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    RoleService roleService;
    @Autowired
    IdeaService ideaService;

    @Override
    public void createUser(NewUserDTO newUserDTO) {
        assertNotBlank(newUserDTO.getFirstName(), "First name");
        assertNotBlank(newUserDTO.getLastName(), "Last name");
        assertNotBlank(newUserDTO.getUsername(), "Login");
        assertNotBlank(newUserDTO.getPassword(), "Password");

        if (getUserByUsername(newUserDTO.getUsername())!=null) {
            throw new IllegalArgumentException("The login is taken.");
        }
        User user = new User();
        user.setFirstName(newUserDTO.getFirstName());
        user.setLastName(newUserDTO.getLastName());
        user.setUsername(newUserDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(newUserDTO.getPassword()));
        user.setRole(roleService.getRoleById(2));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long userId) {
        userRepository.delete(userId);
    }

    @Override
    public User edit(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User getUserByUsername(String username) { return userRepository.findByUsername(username); }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void addBookmark(Idea idea, User user) {
        user.getBookmarkedIdeas().add(idea);
        userRepository.saveAndFlush(user);

        idea.getUsersWhoBookmarked().add(user);
        ideaRepository.saveAndFlush(idea);
    }

    @Override
    public void deleteBookmark(Idea idea, User user) {
        user.getBookmarkedIdeas().remove(idea);
        userRepository.saveAndFlush(user);

        idea.getUsersWhoBookmarked().remove(user);
        ideaRepository.saveAndFlush(idea);
    }

    @Override
    public List<Idea> getBookmarks(User user) {
        return new ArrayList<Idea>(user.getBookmarkedIdeas());
    }
}
