package fmi.pchmi.project.mySchedule.service;

import fmi.pchmi.project.mySchedule.model.database.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final static String SECRET = "VerySecretSecret";
    private final static String CLAIM_USER_ID = "userId";
    private final static Integer EXPIRATION_TIME = 7_200_000; //2 hours in millis

    public String generateToken(User user) {
        return createToken(createClaims(user));
    }

    private String createToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    private Claims createClaims(User user) {
        Claims claims = Jwts.claims();
        claims.put(CLAIM_USER_ID, user.getId());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
        return claims;
    }

    public Claims getClaims(String jwt) {
        try {
            return extractClaims(jwt);
        }
        catch (Exception e) {
            return null;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public boolean validateClaims(Claims claims) {
        return isClaimContained(claims, CLAIM_USER_ID) && !isTokenExpired(claims);
    }

    private Boolean isClaimContained(Claims claims, String claim) { return claims.containsKey(claim); }

    private Boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String getUserId(Claims claims) {
        return claims.get(CLAIM_USER_ID).toString();
    }
}