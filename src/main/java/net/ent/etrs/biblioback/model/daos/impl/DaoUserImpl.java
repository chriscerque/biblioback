package net.ent.etrs.biblioback.model.daos.impl;

import net.ent.etrs.biblioback.model.daos.DaoUser;
import net.ent.etrs.biblioback.model.daos.JpaBaseDao;
import net.ent.etrs.biblioback.model.entities.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Optional;

public class DaoUserImpl extends JpaBaseDao<User, Serializable> implements DaoUser {
    
    @Override
    public Optional<User> findByLogin(String login) {
        TypedQuery<User> q = this.em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class);
        q.setParameter("login", login);
        
        try {
            return Optional.of(q.getSingleResult());
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }
}
