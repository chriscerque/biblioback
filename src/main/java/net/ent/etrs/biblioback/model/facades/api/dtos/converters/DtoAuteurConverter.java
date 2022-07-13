package net.ent.etrs.biblioback.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.entities.Auteur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoAuteur;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoAuteurConverter {
    
    public static DtoAuteur toDto(Auteur auteur) {
        return DtoAuteur.builder()
                .id(auteur.getId())
                .nom(auteur.getNom())
                .prenom(auteur.getPrenom())
                .dateDeNaissance(auteur.getDateDeNaissance())
                .build();
    }
    
    public static Auteur toEntity(DtoAuteur dtoAuteur) {
        Auteur auteur = new Auteur();
        auteur.setId(dtoAuteur.getId());
        auteur.setNom(dtoAuteur.getNom());
        auteur.setPrenom(dtoAuteur.getPrenom());
        auteur.setDateDeNaissance(dtoAuteur.getDateDeNaissance());
        return auteur;
    }
    
    public static List<DtoAuteur> toDtoList(List<Auteur> auteurList) {
        return auteurList.stream().map(DtoAuteurConverter::toDto).collect(Collectors.toList());
    }
    
}
