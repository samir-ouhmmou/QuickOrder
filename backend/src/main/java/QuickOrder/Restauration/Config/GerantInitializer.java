package QuickOrder.Restauration.Config;

import QuickOrder.Restauration.Model.Client;
import QuickOrder.Restauration.Model.Role;
import QuickOrder.Restauration.Repository.ClientRepo;
import QuickOrder.Restauration.Repository.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GerantInitializer {
    @Value("${app.manager.email:}")
    private String managerEmail;

    @Value("${app.manager.password:}")
    private String managerPassword;

    @Value("${app.manager.first-name:}")
    private String managerFirstName;

    @Value("${app.manager.last-name:}")
    private String managerLastName;

    @Value("${app.manager.reset-password:false}")
    private boolean resetPassword;

    @Bean
    CommandLineRunner initializeManager(
            PasswordEncoder passwordEncoder,
            UtilisateurRepo utilisateurRepo,
            ClientRepo clientRepo
    ) {
        return args -> {
            if (managerEmail.isBlank() && managerPassword.isBlank()
                    && managerFirstName.isBlank() && managerLastName.isBlank()) {
                return;
            }

            if (managerEmail.isBlank() || managerPassword.isBlank()
                    || managerFirstName.isBlank() || managerLastName.isBlank()) {
                throw new IllegalStateException(
                        "Les variables du compte gérant doivent toutes être renseignées."
                );
            }

            String normalizedEmail = managerEmail.trim().toLowerCase();
            var existingUser = utilisateurRepo.findByEmail(normalizedEmail);
            if (existingUser.isPresent()) {
                if (existingUser.get().getRole() == Role.GERANT) {
                    if (resetPassword) {
                        existingUser.get().setMotDePass(passwordEncoder.encode(managerPassword));
                        utilisateurRepo.save(existingUser.get());
                    }
                    return;
                }
                throw new IllegalStateException(
                        "L'email du compte gérant est déjà utilisé par un autre rôle."
                );
            }

            Client manager = new Client();
            manager.setPrenom(managerFirstName.trim());
            manager.setNom(managerLastName.trim());
            manager.setEmail(normalizedEmail);
            manager.setActif(true);
            manager.setRole(Role.GERANT);
            manager.setMotDePass(passwordEncoder.encode(managerPassword));
            clientRepo.save(manager);
        };
    }
}
