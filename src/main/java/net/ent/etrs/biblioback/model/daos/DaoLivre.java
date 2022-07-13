package net.ent.etrs.biblioback.model.daos;


import net.ent.etrs.biblioback.model.entities.Livre;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DaoLivre extends BaseDao<Livre, Serializable>{
    
    Optional<Livre> findByIdWithIllustrateurs(Long id);
    
    List<Livre> findAll(int first, int pageSize, Map<String, String> sortedBy, Map<String, String> filterBy);

    int count(Map<String, String> filterBy);
}
