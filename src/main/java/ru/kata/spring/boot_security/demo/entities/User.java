package ru.kata.spring.boot_security.demo.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
//@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    public User() {
    }

    public User(String firstname, String fullname, String email, String password, Set<Role> roles) {
        this.firstname = firstname;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(Long id, String firstname, String fullname, String email, String password, Set<Role> roles) {
        this.id = id;
        this.firstname = firstname;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.LAZY)     //список ролей загружается только по запросу
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),                //ManyToMany потому что у пользователя может быть много ролей и одна роль может бфть присвоена нескольким юзерам
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
                return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return email;
    }               // меняю на почту

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

    @Override
    public boolean isEnabled() {
        return true;
    }


   public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + getRoles() +                   // из-за представления в html
                '}';
    }
}
