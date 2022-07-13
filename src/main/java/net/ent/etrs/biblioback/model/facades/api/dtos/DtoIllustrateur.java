package net.ent.etrs.biblioback.model.facades.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.entities.references.Genre;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoIllustrateur {
    
    private Long id;
    
    private String nom;
    
    private String prenom;
}
