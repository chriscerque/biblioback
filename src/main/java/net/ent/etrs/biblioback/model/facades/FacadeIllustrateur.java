package net.ent.etrs.biblioback.model.facades;

import net.ent.etrs.biblioback.model.daos.DaoIllustrateur;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Illustrateur;
import net.ent.etrs.biblioback.model.entities.Livre;
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
public class FacadeIllustrateur implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private DaoIllustrateur daoIllustrateur;
    
    public List<Illustrateur> findAll() throws DaoException {
        return IterableUtils.toList(this.daoIllustrateur.findAll());
    }
    
    public List<Illustrateur> findByLivre(Long idLivre) throws DaoException {
        return IterableUtils.toList(this.daoIllustrateur.findByLivre(idLivre));
    }
    
    public Optional<Illustrateur> getById(Long id) throws DaoException {
        return this.daoIllustrateur.find(id);
    }
    
    public Optional<Illustrateur> save(Illustrateur illustrateur) throws DaoException {
        return this.daoIllustrateur.save(illustrateur);
    }
    
    public void delete(Long id) throws DaoException {
        this.daoIllustrateur.delete(id);
    }
    
    public boolean exist(Long id) throws DaoException {
        return this.daoIllustrateur.exists(id);
    }
}
