package org.yearup.data;


import org.springframework.web.bind.annotation.RequestBody;
import org.yearup.models.Profile;

import java.security.Principal;

public interface ProfileDao
{
    Profile create(Profile profile);

    // get profile by user id
    Profile getByUserId(int userId);

    // update user profile
    void update(int userId, Profile profile);
}