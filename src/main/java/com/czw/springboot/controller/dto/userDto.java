package com.czw.springboot.controller.dto;

import lombok.Data;

@Data
public class userDto {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String avatarUrl;
    private String token;
}
