package net.ent.etrs.biblioback.model.entities.references;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Genre {
    
    ACTION("Action"),
    SF("Science fiction"),
    ROMANCE("Roman à l'au de rose");
    
    @Getter
    private final String libelle;
    
    
}
