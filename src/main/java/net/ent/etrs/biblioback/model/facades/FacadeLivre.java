package net.ent.etrs.biblioback.model.facades;

import net.ent.etrs.biblioback.model.daos.DaoLivre;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Livre;
import org.apache.commons.collections4.IterableUtils;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Local
@Stateless
public class FacadeLivre implements Serializable {
    
    @Inject
    private DaoLivre daoLivre;
    
    public List<Livre> findAll() throws DaoException {
        return IterableUtils.toList(this.daoLivre.findAll());
    }
    
    public Optional<Livre> getById(Long id) throws DaoException {
        return this.daoLivre.find(id);
    }
    
    public Optional<Livre> save(Livre livre) throws DaoException {
        return this.daoLivre.save(livre);
    }
    
    public void delete(Long id) throws DaoException {
        this.daoLivre.delete(id);
    }
    
    public boolean exist(Long id) throws DaoException {
        return this.daoLivre.exists(id);
    }
    
    public Optional<Livre> findByIdWithIllustrateurs(Long id) {
        return this.daoLivre.findByIdWithIllustrateurs(id);
    }
    
    
    public List<Livre> findAll(int first, int pageSize, Map<String, String> sortedBy, Map<String, String> filterBy) throws DaoException  {
        return this.daoLivre.findAll(first, pageSize, sortedBy, filterBy);
    }

    public int count(Map<String, String> filterBy) throws DaoException  {
        return this.daoLivre.count(filterBy);
    }
}
