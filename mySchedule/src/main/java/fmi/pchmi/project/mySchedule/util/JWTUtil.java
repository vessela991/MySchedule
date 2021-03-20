package fmi.pchmi.project.mySchedule.util;

import fmi.pchmi.project.mySchedule.model.user.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtil {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey("SECRET").parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserPrincipal userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("userId", userDetails.getId() + "");
        claims.put("role", userDetails.getRole());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + 100000L));
        return createToken(claims);
    }

    private String createToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, "SECRET")
                .compact();
    }

    public Boolean validateToken(String token, UserPrincipal userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static Claims decodeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey("SECRET")
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public int getUserId(String token){
        return Integer.parseInt(decodeJWT(token).get("userId").toString());
    }
}