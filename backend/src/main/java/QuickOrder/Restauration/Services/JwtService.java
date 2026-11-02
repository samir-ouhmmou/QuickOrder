package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Model.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {

        this.signingKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        this.expiration = expiration;
    }

    // Générer un token pour un utilisateur
    public String generateToken(Utilisateur utilisateur) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", utilisateur.getId());
        claims.put("role", utilisateur.getRole().name());

        return Jwts.builder()
                .claims(claims)
                .subject(utilisateur.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey)
                .compact();
    }

    // Extraire l'email depuis le token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraire une information particulière du token
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Lire et vérifier le token
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Vérifier si le token est encore valide
    public boolean isTokenValid(String token, Utilisateur utilisateur) {

        String email = extractUsername(token);

        return email.equals(utilisateur.getEmail())
                && extractAllClaims(token).getExpiration().after(new Date());
    }
}