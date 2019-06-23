package com.example.test.service;

import com.example.test.domain.ModifyUserDTO;
import com.example.test.domain.User;

public interface UserService {
    User getUser(int id);

    boolean modify(User user, ModifyUserDTO modifyUser);
}
