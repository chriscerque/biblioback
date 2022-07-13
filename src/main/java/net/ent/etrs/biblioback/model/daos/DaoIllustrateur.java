package net.ent.etrs.biblioback.model.daos;


import net.ent.etrs.biblioback.model.entities.Illustrateur;

import java.io.Serializable;

public interface DaoIllustrateur extends BaseDao<Illustrateur, Serializable>{
    Iterable<Illustrateur> findByLivre(Long idLivre);
}
