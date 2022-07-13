package net.ent.etrs.biblioback.model.entities;

import lombok.*;
import net.ent.etrs.biblioback.model.entities.references.Genre;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@Entity
@Table(name = "LIVRE")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"isbn"})
public class Livre extends AbstractEntity {
    
    @Length(min = 1, max = 50, message = "Le nom du livre doit être compris entre 3 et 10 caractères.")
    @Getter
    @Setter
    @Column(name = "NOM", nullable = false, length = 50)
    private String nom;
    
    
    @Length(min = 3, max = 10, message = "L'isbn du livre doit être compris entre 3 et 10 caractères.")
    @Getter
    @Setter
    @Column(name = "ISBN", nullable = false, length = 10)
    private String isbn;
    
    @Getter
    @Setter
    @Column(name = "DATE_PARUTION", nullable = false)
    private LocalDate dateParution;
    
    @Getter
    @Setter
    @Column(name = "NB_PAGES", nullable = false)
    private Integer nbPages;
    
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTEUR_ID", foreignKey = @ForeignKey(name = "LIVRE__AUTEUR_ID__FK"))
    private Auteur auteur;
    
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "GENRE", nullable = false, length = 50)
    private Genre genre;
    
    @Getter
    @Setter
    @Lob
    @Column(name = "IMAGE", nullable = true)
    private byte[] image;
    
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "LIVRE_ILLUSTRATEUR",
            joinColumns = @JoinColumn(name = "LIVRE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ILLUSTRATEUR_ID"))
    private Set<Illustrateur> illustrateurs = new HashSet<>();
}
