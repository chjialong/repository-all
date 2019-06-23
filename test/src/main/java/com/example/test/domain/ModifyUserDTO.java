package com.example.test.domain;

import lombok.Data;

@Data
public class ModifyUserDTO {
    private String name;
    private Integer age;

    public ModifyUserDTO() {
    }

    public ModifyUserDTO(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
