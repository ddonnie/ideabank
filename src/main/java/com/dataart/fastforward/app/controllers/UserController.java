package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.UserRepository;
import com.dataart.fastforward.app.dto.NewUserDTO;
import com.dataart.fastforward.app.dto.UserInfoDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by Orlov on 25.10.2016.
 */

@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private IdeaService ideaService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/loggedUser")
    public UserInfoDTO getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.getUserByUsername(username);
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setUsername(user.getUsername());
        userInfo.setRole(user.getRole().getRoleName());
        userInfo.setBookmarksCount(user.getBookmarkedIdeas().size());

        return userInfo;
    }

    @GetMapping("/users/loggedUser/bookmarks")
    @ResponseStatus(HttpStatus.OK)
    public Set<Idea> getLoggedUserBookmarks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        User loggedUser = userService.getUserByUsername(loggedUsername);

        Set<Idea> ideas = loggedUser.getBookmarkedIdeas();
        ideaService.setInfoForCurrUser(ideas, loggedUser);

        return ideas;
    }

    @GetMapping("/users/loggedUser/ideas")
    @ResponseStatus(HttpStatus.OK)
    public Set<Idea> getLoggedUserIdeas(@PathVariable String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        User loggedUser = userService.getUserByUsername(loggedUsername);

        Set<Idea> ideas = loggedUser.getIdeas();
        ideaService.setInfoForCurrUser(ideas, loggedUser);

        return ideas;
    }


    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/users/{username}/ideas")
    @ResponseStatus(HttpStatus.OK)
    public Set<Idea> getUserIdeas(@PathVariable String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        User loggedUser = userService.getUserByUsername(loggedUsername);

        User userWithIdeas = userService.getUserByUsername(username);
        Set<Idea> ideas = userWithIdeas.getIdeas();
        ideaService.setInfoForCurrUser(ideas, loggedUser);

        return ideas;
    }

    @PostMapping("/registration")
    public void createUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        userService.createUser(newUserDTO);
    }

    @GetMapping("/users/id={userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

/*    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }*/

}
