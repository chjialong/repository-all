package com.example.test.controller;

import com.example.test.domain.ModifyUserDTO;
import com.example.test.domain.User;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    UserService userService;

    @RequestMapping("get")
    public String get(int id) {
        User user = this.userService.getUser(id);
        return user.toString();
    }

    @RequestMapping("update")
    public Boolean update(int id, String name, Integer age) {
        //动态更新：name和age哪个有值就更新哪个
        User user = this.userService.getUser(id);
        ModifyUserDTO modifyUserDTO = new ModifyUserDTO(name, age);
        return this.userService.modify(user, modifyUserDTO);
    }
}
