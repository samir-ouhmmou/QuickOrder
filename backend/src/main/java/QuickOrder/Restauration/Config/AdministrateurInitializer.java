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
public class AdministrateurInitializer {
    @Value("${app.admin.email:}")
   private String emailadmin;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Value("${app.admin.first-name:}")
    private String adminFirstName;

    @Value("${app.admin.last-name:}")
    private String adminLastName;
@Bean
    CommandLineRunner initializeAdmin(PasswordEncoder passwordEncoder, UtilisateurRepo utilisateurRepo, ClientRepo clientRepo){
    return args -> {
            if(utilisateurRepo.existsByRole(Role.ADMIN)){
                System.out.println("Admoinistrateur existe deja ");
                return;
            }
            if(adminFirstName.isBlank()||adminLastName.isBlank()||emailadmin.isBlank()||adminPassword.isBlank()){
                throw new IllegalStateException("Les variables d'administrateur sont oblegatoires ");

            }
        if (utilisateurRepo.existsByEmail(emailadmin)){
            throw new IllegalStateException(
                    "L'email administrateur est déjà utilisé par un autre compte."
            );
        }
        Client admin = new Client();

        admin.setPrenom(adminFirstName);
        admin.setNom(adminLastName);
        admin.setEmail(emailadmin.trim().toLowerCase());
        admin.setActif(true);
        admin.setRole(Role.ADMIN);
        admin.setMotDePass(passwordEncoder.encode(adminPassword));

        // 5. Enregistrer le compte
        clientRepo.save(admin);

        System.out.println("Compte administrateur initialisé avec succès.");
    };
}
}
