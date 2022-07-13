package net.ent.etrs.biblioback.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.entities.Auteur;
import net.ent.etrs.biblioback.model.entities.Illustrateur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoAuteur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoIllustrateur;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoIllustrateurConverter {
    
    public static DtoIllustrateur toDto(Illustrateur illustrateur) {
        return DtoIllustrateur.builder()
                .id(illustrateur.getId())
                .nom(illustrateur.getNom())
                .prenom(illustrateur.getPrenom())
                .build();
    }
    
    public static Illustrateur toEntity(DtoIllustrateur dtoIllustrateur) {
        Illustrateur illustrateur = new Illustrateur();
        illustrateur.setId(dtoIllustrateur.getId());
        illustrateur.setNom(dtoIllustrateur.getNom());
        illustrateur.setPrenom(dtoIllustrateur.getPrenom());
        return illustrateur;
    }
    
    public static List<DtoIllustrateur> toDtoList(List<Illustrateur> illustrateurList) {
        return illustrateurList.stream().map(DtoIllustrateurConverter::toDto).collect(Collectors.toList());
    }
    
}
