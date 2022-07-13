package net.ent.etrs.biblioback.model.facades;

import net.ent.etrs.biblioback.model.daos.DaoAuteur;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Auteur;
import org.apache.commons.collections4.IterableUtils;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Local
@Stateless
public class FacadeAuteur implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private DaoAuteur daoAuteur;
    
    public List<Auteur> findAll() throws DaoException {
        return IterableUtils.toList(this.daoAuteur.findAll());
    }
    
    public Optional<Auteur> getById(Long id) throws DaoException {
        return this.daoAuteur.find(id);
    }
    
    public Optional<Auteur> save(Auteur auteur) throws DaoException {
        return this.daoAuteur.save(auteur);
    }
    
    public void delete(Long id) throws DaoException {
        this.daoAuteur.delete(id);
    }
    
    public boolean exist(Long id) throws DaoException {
        return this.daoAuteur.exists(id);
    }
    
    public List<Auteur> findAll(int first, int pageSize, Map<String, String> sortedBy, Map<String, String> filterBy) throws DaoException  {
        return this.daoAuteur.findAll(first, pageSize, sortedBy, filterBy);
    }
//
    public int count(Map<String, String> filterBy) throws DaoException {
        return this.daoAuteur.count(filterBy);
    }
}
