package net.ent.etrs.biblioback.model.daos;


import net.ent.etrs.biblioback.model.entities.Auteur;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoAuteur extends BaseDao<Auteur, Serializable> {
    
    List<Auteur> findAll(int first, int pageSize, Map<String, String> sortedBy, Map<String, String> filterBy);

    int count(Map<String, String> filterBy);
}
