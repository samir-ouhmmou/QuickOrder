package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Model.Client;
import QuickOrder.Restauration.Model.Role;
import QuickOrder.Restauration.Model.Utilisateur;
import QuickOrder.Restauration.Repository.ClientRepo;
import QuickOrder.Restauration.Repository.UtilisateurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleAccountService {
    private final UtilisateurRepo utilisateurRepo;
    private final ClientRepo clientRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String authenticate(OAuth2User googleUser) {
        String email = googleUser.getAttribute("email");
        Boolean emailVerified = googleUser.getAttribute("email_verified");
        if (email == null || !Boolean.TRUE.equals(emailVerified)) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_google_account"),
                    "Google must provide a verified email address"
            );
        }

        Utilisateur user = utilisateurRepo.findByEmail(email.trim().toLowerCase(Locale.ROOT))
                .orElseGet(() -> createClient(googleUser, email));
        if (!Boolean.TRUE.equals(user.getActif()) || user.getRole() == null) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("inactive_account"),
                    "The QuickOrder account is not active"
            );
        }

        return jwtService.generateToken(user);
    }

    private Client createClient(OAuth2User googleUser, String email) {
        Client client = new Client();
        client.setEmail(email.trim().toLowerCase(Locale.ROOT));
        client.setPrenom(googleUser.getAttribute("given_name"));
        client.setNom(googleUser.getAttribute("family_name"));
        client.setMotDePass(passwordEncoder.encode(UUID.randomUUID().toString()));
        client.setRole(Role.CLIENT);
        client.setActif(true);
        return clientRepo.save(client);
    }
}
