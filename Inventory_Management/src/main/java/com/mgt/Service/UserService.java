package com.mgt.Service;

import com.mgt.Model.User;

public interface UserService {

    public User  addUser(User user);

    public User loginUser(String email , String password);

    public boolean updateProfile(User user);
}
