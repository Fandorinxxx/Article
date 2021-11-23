package com.article.article.security;

import java.util.Date;
import java.util.Optional;

import com.article.article.conroller.model.Token;
import com.article.article.conroller.model.UserDto;
import com.article.article.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final String jwtSecret;
    private final long jwtExpirationInMs;

    public Optional<Token> getAuthentication(UserDto userDto) {
        var userDetails = userDetailsService.loadUserByUsername(userDto.getName());


        if (new BCryptPasswordEncoder().matches(userDto.getPassword(),userDetails.getPassword())){
            var authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            return isAuthenticated(authentication);
        }
        return Optional.empty();
    }

    public String getUserNameFromJwt(String token) {
        return Jwts.parser()
                   .setSigningKey(jwtSecret)
                   .parseClaimsJws(token)
                   .getBody().getSubject();
    }

    public Token createToken(Authentication authentication) {
        var claims = Jwts.claims().setSubject(authentication.getName());
        claims.put("auth", authentication.getDetails());
        var currentDate = new Date();
        var compact = Jwts.builder()
                          .setClaims(claims)
                          .setIssuedAt(currentDate)
                          .setExpiration(new Date(currentDate.getTime() + jwtExpirationInMs))
                          .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return new Token(compact);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    private Optional<Token> isAuthenticated(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Optional.of(createToken(authentication));
        }
        return Optional.empty();
    }

}