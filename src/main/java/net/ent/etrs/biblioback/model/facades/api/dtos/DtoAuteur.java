package net.ent.etrs.biblioback.model.facades.api.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoAuteur {
    
    private Long id;
    
    @PastOrPresent(message = "L'auteur doit être né")
    private LocalDate dateDeNaissance;
    
    @NotBlank(message = "Le nom doit être renseigné")
    @Length(max = 50, message = "Le nom doit être inférieur à 50 caractères")
    private String nom;
    
    @NotBlank(message = "Le prénom doit être renseigné")
    @Length(max = 50, message = "Le prénom doit être inférieur à 50 caractères")
    private String prenom;
}
