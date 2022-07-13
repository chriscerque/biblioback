package net.ent.etrs.biblioback.model.facades.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.User;
import net.ent.etrs.biblioback.model.facades.FacadeUser;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoUser;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoUserLogin;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.DtoUserConverter;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.exceptions.ConvertException;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.JWTTokenNeeded;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.RoleAdmin;
import net.ent.etrs.biblioback.utils.Hash;
import net.ent.etrs.biblioback.utils.ValidatorUtils;
import net.ent.etrs.biblioback.utils.exceptions.ValidException;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Path("/users")
public class FacadeUserRest {
    
    public static final String SECRE_KEY = "ghJGZrAzghMI2YU29fl8dT1G90on2qRQM0QvUPonH8BUhKG9UTD33aWoyj2ym28CGL7X/sJxgfkNRneeuqw8DA==";
    
    @Inject
    private FacadeUser facadeUser;
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response findAll() {
        try {
            List<User> users = this.facadeUser.findAll();
            return Response.ok(DtoUserConverter.toDtoList(users)).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<User> oUser = this.facadeUser.getById(id);
            if (oUser.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(DtoUserConverter.toDto(oUser.get())).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    @RoleAdmin
    public Response create(DtoUser dtoUser) {
        try {
            ValidatorUtils.validate(dtoUser);
            User user = DtoUserConverter.toEntity(dtoUser);
            user = this.facadeUser.save(user).orElseThrow(DaoException::new);
            return Response.ok(DtoUserConverter.toDto(user)).build();
        } catch (DaoException | ConvertException e) {
            return Response.serverError().build();
        } catch (ValidException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.mapViolations).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    @RoleAdmin
    public Response update(@PathParam("id") Long id, DtoUser dtoUser) {
        try {
            ValidatorUtils.validate(dtoUser);
            if (this.facadeUser.exist(id)) {
                User user = DtoUserConverter.toEntity(dtoUser);
                user = this.facadeUser.save(user).orElseThrow(DaoException::new);
                return Response.ok(DtoUserConverter.toDto(user)).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DaoException | ConvertException e) {
            return Response.serverError().build();
        } catch (ValidException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.mapViolations).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @JWTTokenNeeded
    @RoleAdmin
    public Response delete(@PathParam("id") Long id) {
        try {
            if (this.facadeUser.exist(id)) {
                this.facadeUser.delete(id);
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(DtoUserLogin dtoUserLogin) {
        Optional<User> u = this.facadeUser.findByLogin(dtoUserLogin.getLogin());
        if (u.isPresent()) {
            User user = u.get();
            if (Hash.sha512(dtoUserLogin.getPassword()).equals(user.getPassword())) {
                String token = this.issueToken(user);
                return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    private String issueToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRE_KEY));
        
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .signWith(key)
                .compact();
    }
}
