package com.dm.bookschecker.controller.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dm.bookschecker.domain.dto.UserDTO;
import com.dm.bookschecker.service.UserService;

@RestController
@RequestMapping("/rest/authentication")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public UserDTO isAuthenticated(OAuth2Authentication authentication) {
        if (authentication == null || StringUtils.isEmpty(authentication.getName())) {
            return new UserDTO();
        }
        UserDTO userDTO = userService.getUser(authentication);
        if (userDTO == null) {
            return new UserDTO();
        }

        return userDTO;
    }

}
