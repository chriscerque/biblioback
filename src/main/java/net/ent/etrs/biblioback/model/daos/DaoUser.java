package net.ent.etrs.biblioback.model.daos;


import net.ent.etrs.biblioback.model.entities.User;

import java.io.Serializable;
import java.util.Optional;

public interface DaoUser extends BaseDao<User, Serializable>{
    
    Optional<User> findByLogin(String login);
    
}
