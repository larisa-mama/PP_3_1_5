package ru.kata.spring.boot_security.demo.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.*;



@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                             // только классы-обертки
    @Column(name = "role")
    private String role;
    public Role() {
    }

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role(String role) {
        this.role = role;
    }
public String getWithoutPrefix() {
       return role.substring(5);        // переделала в геттер, потому что обычный метод js не видит, только операнда, метод для вывода в форме без ROLE_
}

    @Override
    public String getAuthority() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "role - "+role;
    }

}
