package net.ent.etrs.biblioback.model.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "AUTEUR")
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Auteur extends AbstractEntity {
   
    @Getter @Setter
    @NotBlank(message = "Le nom doit être renseigné")
    @Length(max = 50, message = "Le nom doit être inférieur à 50 caractères")
    @Column(name = "NOM", nullable = false, length = 50)
    private String nom;
    
    @Getter @Setter
    @NotBlank(message = "Le prénom doit être renseigné")
    @Length(max = 50, message = "Le prénom doit être inférieur à 50 caractères")
    @Column(name = "PRENOM", nullable = false, length = 50)
    private String prenom;
    
    @Getter @Setter
    @PastOrPresent(message = "L'auteur doit être né")
    @Column(name = "DATE_NAISSANCE", nullable = false)
    private LocalDate dateDeNaissance;
    
}
