package se.kth.iv1201.appserv.jobapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class used to handle authentication and operations related to the JWT-token.
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "46294A404E635266556A586E3272357538782F413F4428472B4B615064536756";

    /**
     * Method used to extract the username from the token.
     *
     * @param token the String token sent with the request.
     * @return the username in String format.
     */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Method used to generate a new token.
     *
     * @param userDetails the details of the user to have the token generated.
     * @return a new JWT token to be generated.
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Method used to extract the claims from the token.
     *
     * @param token the JWT token to be extracted.
     * @param claimsResolver used to resolve the claims.
     * @return the claims of the token.
     * @param <T>
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method used to check if a token is valid.
     *
     * @param token the token to be examined.
     * @param userDetails the user details to authenticate the token.
     * @return boolean depending on tokens validity.
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    )
    {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 *24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
