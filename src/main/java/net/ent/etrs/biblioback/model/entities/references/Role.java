package net.ent.etrs.biblioback.model.entities.references;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum Role {
    
    USER("Utilisateur"),
    ADMIN("Administrateur", Role.USER),
    SUPERADMIN("SuperAdmin", Role.ADMIN, Role.USER);
    
    @Getter
    private final String libelle;
    
    private final List<Role> roles;
    
    Role(String libelle, Role... roles) {
        this.libelle = libelle;
        this.roles = Arrays.asList(roles);
    }
    
    public boolean hasRole(Role pRole) {
        return this.equals(pRole) || this.roles.contains(pRole);
    }
    
}
