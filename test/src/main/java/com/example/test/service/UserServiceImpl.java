package com.example.test.service;

import com.example.test.data.UserRepository;
import com.example.test.domain.ModifyUserDTO;
import com.example.test.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUser(int id) {
        return this.userRepository.get(id);
    }

    @Override
    public boolean modify(User user, ModifyUserDTO modifyUser) {
        if (user == null)
            return false;
        user.modify(modifyUser);
        return this.userRepository.update(user) != 0;
    }
}
