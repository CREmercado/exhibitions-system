package com.epam.exhibitions.entity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginUser {

    @NotEmpty(message = "Nickname is mandatory")
    private String nickname;
    @NotEmpty(message = "Password is mandatory")
    private String password;

}
