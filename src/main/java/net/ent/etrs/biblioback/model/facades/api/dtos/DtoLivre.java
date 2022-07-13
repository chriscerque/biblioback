package net.ent.etrs.biblioback.model.facades.api.dtos;

import lombok.*;
import net.ent.etrs.biblioback.model.entities.references.Genre;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLivre {
    
    private Long id;
    
    private String nom;
    
    private String isbn;
    
    private LocalDate dateParution;
    
    private Integer nbPages;
    
    private Long auteur;
    
    private Genre genre;
    
    private String image;
    
    private Set<Long> illustrateurs;
}
