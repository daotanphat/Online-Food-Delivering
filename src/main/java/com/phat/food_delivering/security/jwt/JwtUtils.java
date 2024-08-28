package com.phat.food_delivering.security.jwt;

import com.phat.food_delivering.security.SecurityConstants;
import com.phat.food_delivering.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class JwtUtils {

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .claim("email", (userPrincipal.getEmail()))
                .claim("authorities", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + SecurityConstants.TOKEN_EXPIRATION))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.SECRET_KEY));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: {}" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: {}" + e.getMessage());
        }

        return false;
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add((authority.getAuthority()));
        }
        return String.join(",", auths);
    }
}
