package net.ent.etrs.biblioback.model.facades.api;

import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Auteur;
import net.ent.etrs.biblioback.model.facades.FacadeAuteur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoAuteur;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.DtoAuteurConverter;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.JWTTokenNeeded;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.RoleAdmin;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.RoleUser;
import net.ent.etrs.biblioback.utils.Utils;
import net.ent.etrs.biblioback.utils.ValidatorUtils;
import net.ent.etrs.biblioback.utils.exceptions.ValidException;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
@Path("/auteurs")
@JWTTokenNeeded
public class FacadeAuteurRest {
    
    @Inject
    private FacadeAuteur facadeAuteur;
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(
            @QueryParam("first") @DefaultValue("1") Integer first,
            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize,
            @QueryParam("sortedBy") @DefaultValue("nom:ASC") String sortedBy,
            @QueryParam("filterBy") @DefaultValue("") String filterBy) {
        try {
            Map<String, String> filter = Utils.strToMap(filterBy);
            Map<String, String> sorted = Utils.strToMap(sortedBy);
            
            List<Auteur> auteurs = this.facadeAuteur.findAll(first, pageSize, sorted, filter);
            return Response.ok(DtoAuteurConverter.toDtoList(auteurs)).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Auteur> oAuteur = this.facadeAuteur.getById(id);
            if (oAuteur.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(DtoAuteurConverter.toDto(oAuteur.get())).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RoleAdmin
    public Response create(DtoAuteur dtoAuteur) {
        try {
            ValidatorUtils.validate(dtoAuteur);
            Auteur auteur = DtoAuteurConverter.toEntity(dtoAuteur);
            auteur = this.facadeAuteur.save(auteur).orElseThrow(DaoException::new);
            return Response.ok(DtoAuteurConverter.toDto(auteur)).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        } catch (ValidException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.mapViolations).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RoleAdmin
    public Response update(@PathParam("id") Long id, DtoAuteur dtoAuteur) {
        try {
            ValidatorUtils.validate(dtoAuteur);
            if (this.facadeAuteur.exist(id)) {
                Auteur auteur = DtoAuteurConverter.toEntity(dtoAuteur);
                
                auteur = this.facadeAuteur.save(auteur).orElseThrow(DaoException::new);
                return Response.ok(DtoAuteurConverter.toDto(auteur)).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        } catch (ValidException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.mapViolations).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @RoleAdmin
    public Response delete(@PathParam("id") Long id) {
        try {
            if (this.facadeAuteur.exist(id)) {
                this.facadeAuteur.delete(id);
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count(@QueryParam("filterBy") @DefaultValue("") String filterBy) {
        try {
            Map<String, String> filter = Utils.strToMap(filterBy);
            int count = this.facadeAuteur.count(filter);
            return Response.ok(count).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
}
