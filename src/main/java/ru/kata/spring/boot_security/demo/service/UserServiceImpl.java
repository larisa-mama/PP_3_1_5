package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final RoleDaoImpl roleDao;
    private final UserDaoImpl userDao;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // @Autowired
    public UserServiceImpl(RoleDaoImpl roleDao, UserDaoImpl userDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
    }


    @Transactional
    public void addRole(Role role) {
        Role userOne = roleDao.findByName(role.getRole());
        roleDao.add(role);
    }


    @Transactional(readOnly = true)
    public Role findByNameRole(String email) {
        return roleDao.findByName(email);
    }


    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }


    @Transactional(readOnly = true)
    public Role findByIdRole(Long id) {
        return roleDao.findByIdRole(id);
    }


    @Transactional(readOnly = true)
    public List<Role> listByRole(List<String> email) {
        return roleDao.listByName(email);
    }


    @Transactional
    public void add(User user) {                                        // изменения метода для правильного сохранения в БД, иначе пароль не кодировался
        User userOne = userDao.findByEmail(user.getEmail());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userDao.add(user);

    }


    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }


    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }


    @Transactional
    public void edit(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userDao.edit(user);
    }


    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDao.getUser(id);
    }


    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userOne = findByEmail(email);
        if (userOne == null) {
            throw new UsernameNotFoundException(email + " не найден");
        }
        UserDetails user = new org.springframework.security.core.userdetails.User(userOne.getUsername(),
                userOne.getPassword(), authority(userOne.getRoles()));
        return userOne;
    }

    private Collection<? extends GrantedAuthority> authority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}