package com.example.test.data;

import com.example.test.domain.User;
import com.jialong.repository.ReadRepository;
import com.jialong.repository.WriteRepository;

public interface UserRepository extends ReadRepository<User>, WriteRepository<User> {
}
