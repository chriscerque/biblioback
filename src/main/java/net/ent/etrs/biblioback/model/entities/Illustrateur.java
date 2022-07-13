package net.ent.etrs.biblioback.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ILLUSTRATEUR")
@ToString(of = {"nom", "prenom"})
@EqualsAndHashCode(of = {"nom", "prenom"})
public class Illustrateur extends AbstractEntity {

    @Getter @Setter
    @Column(name = "NOM", length = 50, nullable = false)
    private String nom;
    
    @Getter @Setter
    @Column(name = "PRENOM", length = 50, nullable = false)
    private String prenom;

}
