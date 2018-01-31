package ro.ni.bbs.controller;

import org.springframework.web.bind.annotation.*;
import ro.ni.bbs.dto.UserDTO;
import ro.ni.bbs.service.UserService;

import java.util.List;

/**
 * *  Created by rares on 1/10/2018.
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController
{
    private UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
    public String login(@PathVariable("username") String username, @PathVariable("password") String password)
    {
        System.out.println("Try to login...");
        return userService.findByUsernameAndPassword(username, password);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserDTO> getAll()
    {
        System.out.println("Try to display all users...");
        return userService.getAll();
    }

    @RequestMapping(value = "/register/{username}/{password}", method = RequestMethod.GET)
    public String register(@PathVariable("username") String username, @PathVariable("password") String password)
    {
        System.out.println("Try to register new user...");
        return userService.register(new UserDTO(username, password));
    }
}
