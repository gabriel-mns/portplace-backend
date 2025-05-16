package com.pucpr.portplace.authentication.features.userCrud.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(unique = true)
    private String email;
    @Setter
    @NotBlank
    private String password;
    // @Enumerated(EnumType.ORDINAL)
    // private Role role;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        // this.role = role;
    }

    public void setUsername(String username) {
        this.email = username;
    }

}
