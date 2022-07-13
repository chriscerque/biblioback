package net.ent.etrs.biblioback.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Illustrateur;
import net.ent.etrs.biblioback.model.entities.User;
import net.ent.etrs.biblioback.model.facades.FacadeUser;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoIllustrateur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoUser;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.exceptions.ConvertException;
import net.ent.etrs.biblioback.utils.CDIUtils;
import net.ent.etrs.biblioback.utils.Hash;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoUserConverter {
    
    private static FacadeUser facadeUser;
    
    static {
        DtoUserConverter.facadeUser = CDIUtils.getBean(FacadeUser.class);
    }
    
    public static DtoUser toDto(User user) {
        return DtoUser.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }
    
    public static User toEntity(DtoUser dtoUser) throws ConvertException {
        try {
            User user = new User();
            if (dtoUser.getId() != null) {
                user = DtoUserConverter.facadeUser.getById(dtoUser.getId()).orElse(new User());
            }
            user.setLogin(dtoUser.getLogin());
            if (!Objects.isNull(dtoUser.getPassword())) {
                user.setPassword(Hash.sha512(dtoUser.getPassword()));
            }
            user.setRole(dtoUser.getRole());
            return user;
        } catch (DaoException e) {
            throw new ConvertException(e);
        }
    }
    
    public static List<DtoUser> toDtoList(List<User> userList) {
        return userList.stream().map(DtoUserConverter::toDto).collect(Collectors.toList());
    }
    
}
