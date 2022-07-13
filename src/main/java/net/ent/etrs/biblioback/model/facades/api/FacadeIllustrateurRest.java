package net.ent.etrs.biblioback.model.facades.api;

import lombok.NoArgsConstructor;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Illustrateur;
import net.ent.etrs.biblioback.model.facades.FacadeIllustrateur;
import net.ent.etrs.biblioback.model.facades.api.dtos.DtoIllustrateur;
import net.ent.etrs.biblioback.model.facades.api.dtos.converters.DtoIllustrateurConverter;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.JWTTokenNeeded;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.RoleAdmin;
import net.ent.etrs.biblioback.utils.ValidatorUtils;
import net.ent.etrs.biblioback.utils.exceptions.ValidException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@Path("/illustrateurs")
@JWTTokenNeeded
public class FacadeIllustrateurRest {
    
    @Inject
    private FacadeIllustrateur facadeIllustrateur;
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("livre") Long idLivre) {
        try {
            List<Illustrateur> illustrateurs;
            if (Objects.isNull(idLivre)) {
                illustrateurs = this.facadeIllustrateur.findAll();
            } else {
                illustrateurs = this.facadeIllustrateur.findByLivre(idLivre);
            }
            return Response.ok(DtoIllustrateurConverter.toDtoList(illustrateurs)).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Illustrateur> oIllustrateur = this.facadeIllustrateur.getById(id);
            if (oIllustrateur.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(DtoIllustrateurConverter.toDto(oIllustrateur.get())).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RoleAdmin
    public Response create(DtoIllustrateur dtoIllustrateur) {
        try {
            ValidatorUtils.validate(dtoIllustrateur);
            Illustrateur illustrateur = DtoIllustrateurConverter.toEntity(dtoIllustrateur);
            illustrateur = this.facadeIllustrateur.save(illustrateur).orElseThrow(DaoException::new);
            return Response.ok(DtoIllustrateurConverter.toDto(illustrateur)).build();
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
    public Response update(@PathParam("id") Long id, DtoIllustrateur dtoIllustrateur) {
        try {
            ValidatorUtils.validate(dtoIllustrateur);
            if (this.facadeIllustrateur.exist(id)) {
                Illustrateur illustrateur = DtoIllustrateurConverter.toEntity(dtoIllustrateur);
                illustrateur = this.facadeIllustrateur.save(illustrateur).orElseThrow(DaoException::new);
                return Response.ok(DtoIllustrateurConverter.toDto(illustrateur)).build();
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
            if (this.facadeIllustrateur.exist(id)) {
                this.facadeIllustrateur.delete(id);
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DaoException e) {
            return Response.serverError().build();
        }
    }
}
