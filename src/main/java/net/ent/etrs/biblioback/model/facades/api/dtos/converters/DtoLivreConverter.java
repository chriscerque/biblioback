package net.ent.etrs.biblioback.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Auteur;
import net.ent.etrs.biblioback.model.entities.Illustrateur;
import net.ent.etrs.biblioback.model.entities.Livre;
import net.ent.etrs.biblioback.model.facades.FacadeAuteur;
import net.ent.etrs.biblioback.model.facades.FacadeIllustrateur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoAuteur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoLivre;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.exceptions.ConvertException;
import net.ent.etrs.biblioback.utils.CDIUtils;

import javax.inject.Inject;
import java.util.Base64;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoLivreConverter {
    
    private static FacadeAuteur facadeAuteur;
    private static FacadeIllustrateur facadeIllustrateur;
    
    static {
        DtoLivreConverter.facadeAuteur = CDIUtils.getBean(FacadeAuteur.class);
        DtoLivreConverter.facadeIllustrateur = CDIUtils.getBean(FacadeIllustrateur.class);
    }
    
    public static DtoLivre toDto(Livre livre) {
        return DtoLivre.builder()
                .id(livre.getId())
                .dateParution(livre.getDateParution())
                .genre(livre.getGenre())
                .isbn(livre.getIsbn())
                .image(livre.getImage() != null ? Base64.getEncoder().encodeToString(livre.getImage()) : null)
                .nbPages(livre.getNbPages())
                .nom(livre.getNom())
                .auteur(livre.getAuteur().getId())
                .build();
    }
    
    
    public static Livre toEntity(DtoLivre dtoLivre) throws ConvertException {
        try {
            Livre livre = new Livre();
            livre.setId(dtoLivre.getId());
            livre.setDateParution(dtoLivre.getDateParution());
            livre.setGenre(dtoLivre.getGenre());
            livre.setIsbn(dtoLivre.getIsbn());
            if (dtoLivre.getImage()!=null && !dtoLivre.getImage().isBlank()) {
                livre.setImage(Base64.getDecoder().decode(dtoLivre.getImage()));
            }
            if (dtoLivre.getIllustrateurs() != null) {
                for (Long idIllustrateur : dtoLivre.getIllustrateurs()) {
                    Optional<Illustrateur> oIllustrateur = DtoLivreConverter.facadeIllustrateur.getById(idIllustrateur);
                    oIllustrateur.ifPresent(illustrateur -> livre.getIllustrateurs().add(illustrateur));
                }
            }
            livre.setNbPages(dtoLivre.getNbPages());
            livre.setNom(dtoLivre.getNom());
            livre.setAuteur(DtoLivreConverter.facadeAuteur.getById(dtoLivre.getAuteur()).orElse(null));
            return livre;
        } catch (DaoException e) {
            throw new ConvertException(e);
        }
    }
    
    public static List<DtoLivre> toDtoList(List<Livre> livreList) {
        return livreList.stream().map(DtoLivreConverter::toDto).collect(Collectors.toList());
    }
    
}
