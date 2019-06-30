package com.example.demo.data.user;

import com.example.demo.domain.User;
import com.jialong.repository.ReadRepository;
import com.jialong.repository.WriteRepository;

public interface UserRepository extends ReadRepository<User>, WriteRepository<User> {
}
