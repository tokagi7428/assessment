package com.kbtg.bootcamp.posttest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
public class UserModel implements UserDetails {

    @Column(name = "roles")
    @NotNull
    Set<String> roles = new HashSet<>();

    @Column(name = "permissions")
    @NotNull
    Set<String> permissions = new HashSet<>();

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(name = "user_id")
    @Size(min = 10, max = 10)
    @NotNull
    private String userId;

    public UserModel(){}

    public UserModel(String username, String password) {
        this.userId = UUID.randomUUID().toString().substring(0,10);
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role:roles){
            System.out.println("role : " + role);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        for(String permission:permissions){
            authorities.add(new SimpleGrantedAuthority(permission));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setRoles(List<String> roles) {
        this.roles.addAll(roles);
    }

    public void setPermissions(List<String> permissions) {
        this.permissions.addAll(permissions);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
