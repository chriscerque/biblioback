package net.ent.etrs.biblioback.model.facades.api.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.ent.etrs.biblioback.model.entities.User;
import net.ent.etrs.biblioback.model.entities.references.Role;
import net.ent.etrs.biblioback.model.facades.FacadeUser;
import net.ent.etrs.biblioback.model.facades.api.FacadeUserRest;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.RoleAdmin;
import net.ent.etrs.biblioback.model.facades.api.filters.annotations.RoleUser;

import javax.annotation.Priority;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Provider
@RoleAdmin
@Priority(Priorities.AUTHORIZATION)
public class RoleAdminFilter implements ContainerRequestFilter {
    
    @Inject
    private FacadeUser facadeUser;
    
    @Override
    public void filter(ContainerRequestContext rc) throws IOException {
        String authorizationHeader = rc.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer".length()).trim();
    
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(FacadeUserRest.SECRE_KEY));
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        Jws<Claims> claims = jwtParser.parseClaimsJws(token);
    
        String login = claims.getBody().getSubject();
    
        Optional<User> oUser = this.facadeUser.findByLogin(login);
        
        if (!oUser.get().getRole().hasRole(Role.ADMIN)) {
            this.abort(rc);
        }
    }
    
    private void abort(ContainerRequestContext rc) {
        rc.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
