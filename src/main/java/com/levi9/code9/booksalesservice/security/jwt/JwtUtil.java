package com.levi9.code9.booksalesservice.security.jwt;

import com.levi9.code9.booksalesservice.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY="kd73ds8f";

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<GrantedAuthority> extractAuthorities(String token){
        final Claims claims = extractAllClaims(token);
        ArrayList<LinkedHashMap> authoritiesList = (ArrayList) claims.get("authorities");

        LinkedList<GrantedAuthority> authorities= new LinkedList<GrantedAuthority>();
        for (LinkedHashMap authorityMap : authoritiesList) {
            SimpleGrantedAuthority grantedAuthority= new SimpleGrantedAuthority(authorityMap.get("authority").toString());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }

    public Long extractId(String token){
        final Claims claims = extractAllClaims(token);
        final Long id = claims.get("id", Long.class);
        return id;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
}
