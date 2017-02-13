package ua.sernikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ua.sernikov.domain.NewUserRequest;
import ua.sernikov.domain.User;
import ua.sernikov.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{key}")
    public User getUserByKey(@PathVariable("key") String userKey) {
        Assert.hasText(userKey);
        return userService.getUserByKey(userKey);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody @Valid NewUserRequest newUser) {
        return userService.createUser(newUser);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{key}")
    public User updateUser(@PathVariable("key") String userKey, @RequestBody @Valid User user) {
        user.setKey(userKey);
        return userService.updateUser(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{key}")
    public void deleteUser(@PathVariable("key") String userKey) {
        Assert.hasText(userKey);
        userService.deleteUserByKey(userKey);
    }
}
