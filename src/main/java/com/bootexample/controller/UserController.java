package com.bootexample.controller;

import com.bootexample.auth.TokenManager;
import com.bootexample.entity.User;
import com.bootexample.exception.BadRequestException;
import com.bootexample.exception.ConflictException;
import com.bootexample.exception.NotFoundException;
import com.bootexample.model.Count;
import com.bootexample.model.UserResponse;
import com.bootexample.repository.UserRepository;
import com.bootexample.util.Constants;
import com.bootexample.util.Specifications;
import com.bootexample.validation.ModelValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.bootexample.util.BootUtils.isNOE;

/**
 * Created by antosha4e on 11.05.16.
 */
@RestController
public class UserController {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TokenManager tokenManager;

    /**
     *  Return list of users, except super-user
     * @param pageId page to show
     * @param pageSize Item on page to show (10 by default)
     * @param sort Field to sort by, +/- prefix defines order (ACS/DESC)
     * @return Requested users
     */
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    @Secured("ROLE_AUTH")
    public Collection<User> users(@RequestParam(value = "pageId", required = false, defaultValue = "1") int pageId,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                  @RequestParam(value = "sort",required = false) String sort) {

        List<User> list = new ArrayList<>();
        userRepository.findAll(Specifications.findAllUsers(sort)).iterator().forEachRemaining(list::add);

        int from = pageSize * (pageId - 1);
        int to = pageSize * pageId;

        // todo refactor this when comes to Postgresql, use limit and offset instead

        if(list.size() < to) {
            return list;
        }

        return list.subList(from, to);
    }

    /**
     * Return count of users, except super-user
     * @return Count of users
     */
    @RequestMapping(value = "/api/users/count", method = RequestMethod.GET)
    @Secured("ROLE_AUTH")
    public Count usersCount() {
        return new Count(userRepository.count());
    }

    /**
     * Add new user
     * @param user User to add. Password is plain-text
     * @return Id
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    @Secured("ROLE_AUTH")
    public UserResponse addUser(@RequestBody User user) {
        if(ModelValidation.isUserValid(user)) {
            throw new BadRequestException("Invalid values in fields");
        }

        String plainPassword = user.getPassword();

        try {
            user.setPassword(user.getPassword());
            user = userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            throw new ConflictException("User with such email exists");
        }

        UserResponse response = new UserResponse();

        response.setId(user.getId());

        return response;
    }

    /**
     * Return information about one user
     * @param userId id of user
     * @return User data
     */
    @RequestMapping(value = "/api/user/{userId}", method = RequestMethod.GET)
    @Secured("ROLE_AUTH")
    public User getUserById(@PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId);

        if(user == null) {
            throw new NotFoundException("No such user");
        }

        return user;
    }

    /**
     * Update user. Password is not in request (if not changed) or plain-text
     * @param userId id of user
     * @param userFrom Updated user
     */
    @RequestMapping(value = "/api/user/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(value= HttpStatus.OK, reason = "Updated")
    @Secured("ROLE_AUTH")
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody User userFrom) {
        if(!isNOE(userFrom.getEmail()) && ModelValidation.isEmailValid(userFrom.getEmail())) {
            throw new BadRequestException("Invalid values in fields");
        }

        User user = userRepository.findById(userId);

        if(!isNOE(userFrom.getPassword())) {
            user.setPassword(userFrom.getPassword());
        }

        if(!isNOE(userFrom.getName())) {
            user.setName(userFrom.getName());
        }

        if(userFrom.getModules() != null) {
            user.setModules(userFrom.getModules());
        }

        try {
            userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            throw new ConflictException("User with such email exists");
        }
    }

    /**
     * Delete user
     * @param userId id of user
     */
    @RequestMapping(value = "/api/user/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(value= HttpStatus.OK, reason = "Deleted")
    @Secured("ROLE_AUTH")
    public void deleteUserById(@PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId);

        if(user == null) {
            throw new NotFoundException("No such user");
        }

        userRepository.delete(user);
    }
}