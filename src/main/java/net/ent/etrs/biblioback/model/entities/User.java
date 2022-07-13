package net.ent.etrs.biblioback.model.entities;


import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.biblioback.model.entities.references.Role;

import javax.persistence.*;

@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(name = "USER__LOGIN__UK", columnNames = "LOGIN"))
public class User extends AbstractEntity {
    
    @Getter
    @Setter
    @Column(name = "LOGIN", length = 50, nullable = false)
    private String login;
    
    @Getter
    @Setter
    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String password;
    
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", length = 15, nullable = false)
    private Role role;
}
