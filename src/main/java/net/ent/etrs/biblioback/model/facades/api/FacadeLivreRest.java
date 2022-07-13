package net.ent.etrs.biblioback.model.facades.api;

import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Auteur;
import net.ent.etrs.biblioback.model.entities.Livre;
import net.ent.etrs.biblioback.model.facades.FacadeAuteur;
import net.ent.etrs.biblioback.model.facades.FacadeLivre;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoAuteur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoLivre;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.DtoAuteurConverter;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.DtoLivreConverter;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.exceptions.ConvertException;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.JWTTokenNeeded;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.RoleAdmin;
import net.ent.etrs.biblioback.utils.Utils;
import net.ent.etrs.biblioback.utils.ValidatorUtils;
import net.ent.etrs.biblioback.utils.exceptions.ValidException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
@Path("/livres")
@JWTTokenNeeded
public class FacadeLivreRest {
    
    @Inject
    private FacadeLivre facadeLivre;
    
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
        
            List<Livre> livres = this.facadeLivre.findAll(first, pageSize, sorted, filter);
            return Response.ok(DtoLivreConverter.toDtoList(livres)).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Livre> oLivre = this.facadeLivre.getById(id);
            if (oLivre.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(DtoLivreConverter.toDto(oLivre.get())).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RoleAdmin
    public Response create(DtoLivre dtoLivre) {
        try {
            ValidatorUtils.validate(dtoLivre);
            Livre livre = DtoLivreConverter.toEntity(dtoLivre);
            livre = this.facadeLivre.save(livre).orElseThrow(DaoException::new);
            return Response.ok(DtoLivreConverter.toDto(livre)).build();
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
    @RoleAdmin
    public Response update(@PathParam("id") Long id, DtoLivre dtoLivre) {
        try {
            ValidatorUtils.validate(dtoLivre);
            if (this.facadeLivre.exist(id)) {
                Livre livre = DtoLivreConverter.toEntity(dtoLivre);
                livre = this.facadeLivre.save(livre).orElseThrow(DaoException::new);
                return Response.ok(DtoLivreConverter.toDto(livre)).build();
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
    @RoleAdmin
    public Response delete(@PathParam("id") Long id) {
        try {
            if (this.facadeLivre.exist(id)) {
                this.facadeLivre.delete(id);
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
            int count = this.facadeLivre.count(filter);
            return Response.ok(count).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
}
