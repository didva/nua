package com.dm.bookschecker.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dm.bookschecker.dao.UserDao;
import com.dm.bookschecker.domain.dto.RegistrationInfoDTO;
import com.dm.bookschecker.domain.dto.UserDTO;
import com.dm.bookschecker.domain.model.User;
import com.dm.bookschecker.domain.model.UserRole;
import com.dm.bookschecker.service.utils.PasswordGenerator;

@Service
public class UserService implements UserDetailsService, ClientDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordGenerator passwordGenerator;
    @Autowired
    private TokenStore tokenStore;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.find(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getUserRoles().stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(toList()));
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public RegistrationInfoDTO createUser(RegistrationInfoDTO newUserInfo) {
        String tempPassword = passwordGenerator.generatePassword();
        String encodedPassword = passwordEncoder.encode(tempPassword);
        User user = new User(newUserInfo.getUsername(), encodedPassword, newUserInfo.getRoles());
        newUserInfo.setId(userDao.save(user).getUserId());
        newUserInfo.setPassword(tempPassword);
        return newUserInfo;
    }

    @Transactional
    public void changePassword(OAuth2Authentication authentication, long id, String password) {
        if (authentication == null || StringUtils.isEmpty(authentication.getName())) {
            throw new AccessDeniedException("Not authorized");
        }
        User user = userDao.find(id);
        User initiator = userDao.find(authentication.getName());
        if (user == null || initiator == null) {
            throw new IllegalArgumentException();
        }
        if (!initiator.getUserRoles().contains(UserRole.ROLE_ADMIN) || initiator.getUserId() != user.getUserId()) {
            throw new AccessDeniedException("Not enough permissions");
        }
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userDao.update(user);
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public void updateUser(long id, UserDTO userDTO) {
        User user = userDao.find(id);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        user.setUserRoles(userDTO.getRoles());
        userDao.update(user);
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public List<UserDTO> getUsers(OAuth2Authentication authentication) {
        List<User> users = userDao.find();
        return users.stream().map(u -> getUserDTO(authentication, u)).collect(toList());
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public UserDTO getUser(OAuth2Authentication authentication) {
        User user = userDao.find(authentication.getName());
        return getUserDTO(authentication, user);
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public UserDTO getUser(OAuth2Authentication authentication, long id) {
        User user = userDao.find(id);
        return getUserDTO(authentication, user);
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public void deleteUser(OAuth2Authentication authentication, long id) {
        User user = userDao.find(id);
        if (authentication != null && Objects.equals(user.getUsername(), authentication.getName())) {
            throw new IllegalArgumentException("You can't kill yourself");
        }
        userDao.delete(user);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        User user = userDao.find(clientId);
        if (user == null) {
            throw new NoSuchClientException(clientId);
        }
        String roles = String.join(",", user.getUserRoles().stream().map(Enum::toString).collect(toList()));
        BaseClientDetails clientDetails = new BaseClientDetails(clientId, "", "read,write,trust",
                "client_credentials,refresh_token", roles);
        clientDetails.setClientSecret(user.getPassword());
        return clientDetails;
    }

    private UserDTO getUserDTO(OAuth2Authentication authentication, User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoles(user.getUserRoles());
        if (authentication != null && Objects.equals(user.getUsername(), authentication.getName())) {
            OAuth2AccessToken oAuth2AccessToken = tokenStore.getAccessToken(authentication);
            userDTO.setExpiresIn(oAuth2AccessToken.getExpiresIn());
            userDTO.setToken(oAuth2AccessToken.getValue());
        }
        return userDTO;
    }
}
