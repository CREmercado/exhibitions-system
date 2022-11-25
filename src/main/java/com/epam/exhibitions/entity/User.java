package com.epam.exhibitions.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

// TODO: Let's use Validation messages with different locals
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    // variables or columns
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotEmpty(message = "First name is mandatory")
    private String firstName;
    @NotEmpty(message = "Last name is mandatory")
    private String lastName;
    @NotEmpty(message = "Nickname is mandatory")
    private String nickname;
    @NotEmpty(message = "Password is mandatory")
    private String password;
    @NotEmpty(message = "Role is mandatory")
    private String role;

    public User(User user) {
        this.userId=user.getUserId();
        this.nickname=user.getNickname();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.password=user.getPassword();
        this.role=user.getRole();
    }

}
