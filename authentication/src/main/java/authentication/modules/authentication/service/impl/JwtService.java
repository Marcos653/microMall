package authentication.modules.authentication.service.impl;

import authentication.modules.authentication.service.IJwtService;
import authentication.modules.common.exception.CustomTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService implements IJwtService {

    private static final int TOKEN_EXPIRATION_IN_ONE_DAY = 1000 * 60 * 60 * 24;

    private final Key signingKey;

    public JwtService(@Value("${token.signing.key}") String jwtSigningKey) {
        var keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateTokenForUser(userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return Optional.ofNullable(userDetails)
                .map(UserDetails::getUsername)
                .filter(username -> username.equals(extractUserName(token)) && !isTokenExpired(token))
                .isPresent();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final var claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateTokenForUser(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_IN_ONE_DAY))
                .signWith(signingKey, SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            log.error("JWT token has expired. Token: {}, Error: {}", token, ex.getMessage());
            throw new CustomTokenException("JWT token has expired.", ex);
        } catch (JwtException ex) {
            log.error("Error parsing JWT. Token: {}, Error: {}", token, ex.getMessage());
            throw new CustomTokenException("Error parsing JWT.", ex);
        }
    }
}
