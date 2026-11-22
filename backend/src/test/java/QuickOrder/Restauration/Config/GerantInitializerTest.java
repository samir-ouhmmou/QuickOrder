package QuickOrder.Restauration.Config;

import QuickOrder.Restauration.Model.Client;
import QuickOrder.Restauration.Model.Role;
import QuickOrder.Restauration.Model.Utilisateur;
import QuickOrder.Restauration.Repository.ClientRepo;
import QuickOrder.Restauration.Repository.UtilisateurRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GerantInitializerTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UtilisateurRepo utilisateurRepo;
    @Mock
    private ClientRepo clientRepo;

    @Test
    void createsConfiguredManagerWithNormalizedEmailAndEncodedPassword() throws Exception {
        GerantInitializer initializer = configuredInitializer(
                "  manager@example.com  ", "secret-password", " Samir ", " Ouhmmou "
        );
        when(utilisateurRepo.findByEmail("manager@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secret-password")).thenReturn("encoded-password");

        CommandLineRunner runner = initializer.initializeManager(passwordEncoder, utilisateurRepo, clientRepo);
        runner.run();

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepo).save(captor.capture());
        Client created = captor.getValue();
        assertEquals("manager@example.com", created.getEmail());
        assertEquals("Samir", created.getPrenom());
        assertEquals("Ouhmmou", created.getNom());
        assertEquals(Role.GERANT, created.getRole());
        assertEquals("encoded-password", created.getMotDePass());
        assertEquals(true, created.getActif());
    }

    @Test
    void refusesToUseAnEmailAssignedToAnotherRole() {
        GerantInitializer initializer = configuredInitializer(
                "manager@example.com", "secret-password", "Samir", "Ouhmmou"
        );
        Utilisateur existingUser = new Utilisateur();
        existingUser.setRole(Role.ADMIN);
        when(utilisateurRepo.findByEmail("manager@example.com")).thenReturn(Optional.of(existingUser));

        CommandLineRunner runner = initializer.initializeManager(passwordEncoder, utilisateurRepo, clientRepo);

        assertThrows(IllegalStateException.class, runner::run);
        verify(clientRepo, never()).save(any(Client.class));
    }

    @Test
    void resetsExistingManagerPasswordWhenExplicitlyRequested() throws Exception {
        GerantInitializer initializer = configuredInitializer(
                "manager@example.com", "new-password", "Samir", "Ouhmmou"
        );
        ReflectionTestUtils.setField(initializer, "resetPassword", true);

        Client existingManager = new Client();
        existingManager.setEmail("manager@example.com");
        existingManager.setRole(Role.GERANT);
        existingManager.setMotDePass("old-password");
        when(utilisateurRepo.findByEmail("manager@example.com")).thenReturn(Optional.of(existingManager));
        when(passwordEncoder.encode("new-password")).thenReturn("encoded-new-password");

        CommandLineRunner runner = initializer.initializeManager(passwordEncoder, utilisateurRepo, clientRepo);
        runner.run();

        assertEquals("encoded-new-password", existingManager.getMotDePass());
        verify(utilisateurRepo).save(existingManager);
    }

    private GerantInitializer configuredInitializer(
            String email, String password, String firstName, String lastName
    ) {
        GerantInitializer initializer = new GerantInitializer();
        ReflectionTestUtils.setField(initializer, "managerEmail", email);
        ReflectionTestUtils.setField(initializer, "managerPassword", password);
        ReflectionTestUtils.setField(initializer, "managerFirstName", firstName);
        ReflectionTestUtils.setField(initializer, "managerLastName", lastName);
        return initializer;
    }
}
