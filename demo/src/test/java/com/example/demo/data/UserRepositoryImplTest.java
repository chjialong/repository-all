package com.example.demo.data;

import com.example.demo.DemoApplicationTests;
import com.example.demo.data.user.UserRepository;
import com.example.demo.data.user.queryFilters.UserReportQueryFilter;
import com.example.demo.data.user.queryFilters.UserSimpleQueryFilter;
import com.example.demo.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class UserRepositoryImplTest extends DemoApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void getTest() {
        User user = this.userRepository.get(1);
        Assert.assertNotNull(user);
    }

    @Test
    public void updateTest1() {
        int result = 0;
        User user = this.userRepository.get(1);
        user.setAge(10);
        /*
        生成的SQL：
        update tb_user set age = ? where id = ?
         */
        result = this.userRepository.update(user);
        Assert.assertTrue(result == 1);

        user.setName(new Date().toString());
        /*
        生成的SQL：
        update tb_user set name = ? where id = ?
         */
        result = this.userRepository.update(user);
        Assert.assertTrue(result == 1);

        user.setAge(20);
        user.setName(new Date().toString());
        /*
        生成的SQL：
        update tb_user set age = ?, name = ? where id = ?
         */
        result = this.userRepository.update(user);
        Assert.assertTrue(result == 1);
    }

    @Test
    public void insertTest() {
        User user = new User();
        user.setAge(1);
        user.setName(new Date().toString());
        user.setUpdateTime(new Date());
        Object result = this.userRepository.insert(user);
        Assert.assertNotNull(result);
    }

    @Test
    public void queryListTest1() {
        UserSimpleQueryFilter filter = new UserSimpleQueryFilter();
        List<User> users = this.userRepository.find(filter);
        Assert.assertNotNull(users);

        filter.setAge(20);
        users = this.userRepository.find(filter);
        Assert.assertNotNull(users);
    }

    @Test
    public void queryListTest2() {
        UserReportQueryFilter filter = new UserReportQueryFilter();
        filter.setAge(1);
        Integer count = this.userRepository.get(filter);
        Assert.assertNotNull(count);
    }
}
