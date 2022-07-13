package net.ent.etrs.biblioback.model.daos.impl;

import net.ent.etrs.biblioback.model.daos.DaoIllustrateur;
import net.ent.etrs.biblioback.model.daos.JpaBaseDao;
import net.ent.etrs.biblioback.model.entities.Illustrateur;

import java.io.Serializable;

public class DaoIllustrateurImpl extends JpaBaseDao<Illustrateur, Serializable> implements DaoIllustrateur {
    @Override
    public Iterable<Illustrateur> findByLivre(Long idLivre) {
        return this.getEm()
                .createQuery(
                        "SELECT i FROM Illustrateur i WHERE i IN (SELECT i FROM Livre l LEFT JOIN l.illustrateurs i WHERE l.id = :id)"
                        , Illustrateur.class)
                .setParameter("id", idLivre)
                .getResultList();
    }
}
