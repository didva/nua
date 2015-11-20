package com.dm.bookschecker.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dm.bookschecker.domain.dto.RegistrationInfoDTO;
import com.dm.bookschecker.domain.dto.RestResultDTO;
import com.dm.bookschecker.domain.dto.UserDTO;
import com.dm.bookschecker.service.UserService;

@RestController
@RequestMapping("/rest/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public RegistrationInfoDTO saveUser(@RequestBody RegistrationInfoDTO user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResultDTO updateUser(@PathVariable long id, @RequestBody UserDTO user) {
        userService.updateUser(id, user);
        return new RestResultDTO(true);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> getUsers(OAuth2Authentication authentication) {
        return userService.getUsers(authentication);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResultDTO deleteUser(@PathVariable long id, OAuth2Authentication authentication) {
        userService.deleteUser(authentication, id);
        return new RestResultDTO(true);
    }

    @RequestMapping(value = "/password/{id}", method = RequestMethod.POST)
    public RestResultDTO updatePassword(@PathVariable long id, @RequestBody String password,
            OAuth2Authentication authentication) {
        userService.changePassword(authentication, id, password);
        return new RestResultDTO(true);
    }

}
