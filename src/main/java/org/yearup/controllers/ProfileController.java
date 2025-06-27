package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
@RestController
// add the annotation to make this controller the endpoint for the following url
// http://localhost:8080/profile
@RequestMapping("profile")
// add annotation to allow cross site origin requests
@CrossOrigin
// only logged-in users should have access to these actions
@PreAuthorize("hasRole('ROLE_USER')")
public class ProfileController
{
    // a profile requires
    private ProfileDao profileDao;
    private UserDao userDao;

    // constructor
    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao)
    {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    // get profile by user id
    @GetMapping
    public Profile getProfile(Principal principal)
    {
        try
        {
            String userName = principal.getName();
            int userId = userDao.getByUserName(userName).getId();

            return profileDao.getByUserId(userId);
        }
        catch (Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // update user profile
    @PutMapping
    public void updateProfile(@RequestBody Profile profile, Principal principal)
    {
        try
        {
            String userName = principal.getName();
            int userId = userDao.getByUserName(userName).getId();
            profile.setUserId(userId);
            profileDao.update(userId, profile);
        }
        catch (Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}