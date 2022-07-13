package net.ent.etrs.biblioback.model.facades.api.dtos;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserLogin {
    
    private String login;
    
    private String password;
    
}
