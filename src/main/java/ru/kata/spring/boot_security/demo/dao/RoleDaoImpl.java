package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext                     //предназаначена для автоматического связывания менеджера сущностей с бином.
    private EntityManager entityManager;

    public void add(Role user) {
        entityManager.persist(user);            //Чтобы изменить состояние сущности с Transient (New) на Managed (Persisted)
            }

    @Override
    public Role findByIdRole(Long id) {
        return entityManager.find(Role.class, id);
    }


    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select s from Role s", Role.class).getResultList();
    }

    @Override
    public Role findByName(String email) {
        return entityManager.createQuery("select u FROM Role u where u.role= :id", Role.class).setParameter("id", email).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Role> listByName(List<String> email) {
        return entityManager.createQuery("select u from Role u where u.role in (:id)", Role.class).setParameter("id", email).getResultList();
    }
}
