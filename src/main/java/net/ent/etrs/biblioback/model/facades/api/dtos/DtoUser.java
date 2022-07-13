package net.ent.etrs.biblioback.model.facades.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.entities.references.Genre;
import net.ent.etrs.biblioback.model.entities.references.Role;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {
    
    private Long id;
    
    private String login;
    
    private String password;
    
    private Role role;
}
