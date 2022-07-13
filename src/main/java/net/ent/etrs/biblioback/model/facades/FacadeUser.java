package net.ent.etrs.biblioback.model.facades;

import net.ent.etrs.biblioback.model.daos.DaoUser;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Livre;
import net.ent.etrs.biblioback.model.entities.User;
import org.apache.commons.collections4.IterableUtils;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Local
@Stateless
public class FacadeUser implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private DaoUser daoUser;
    
    public List<User> findAll() throws DaoException {
        return IterableUtils.toList(this.daoUser.findAll());
    }
    
    public Optional<User> getById(Long id) throws DaoException {
        return this.daoUser.find(id);
    }
    
    public Optional<User> save(User user) throws DaoException {
        return this.daoUser.save(user);
    }
    
    public void delete(Long id) throws DaoException {
        this.daoUser.delete(id);
    }
    
    public boolean exist(Long id) throws DaoException {
        return this.daoUser.exists(id);
    }
    
    public Optional<User> findByLogin(String login) {
        return this.daoUser.findByLogin(login);
    }
    
    
}
