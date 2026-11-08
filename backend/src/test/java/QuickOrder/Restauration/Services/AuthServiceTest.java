// package QuickOrder.Restauration.Services;

// import QuickOrder.Restauration.Dto.LoginrequestDto;
// import QuickOrder.Restauration.Model.Role;
// import QuickOrder.Restauration.Model.Utilisateur;
// import QuickOrder.Restauration.Repository.UtilisateurRepo;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.server.ResponseStatusException;

// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.never;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// class AuthServiceTest {
//     private static final String EMAIL = "admin@example.com";
//     private static final String PASSWORD = "samir123";

//     @Mock
//     private UtilisateurRepo utilisateurRepo;

//     @Mock
//     private JwtService jwtService;

//     private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//     private AuthService authService;

//     @BeforeEach
//     void setUp() {
//         authService = new AuthService(utilisateurRepo, passwordEncoder, jwtService);
//     }

//     @Test
//     void migratesLegacyPlaintextPasswordAfterSuccessfulLogin() {
//         Utilisateur utilisateur = activeAdminWithPassword(PASSWORD);
//         when(utilisateurRepo.findByEmail(EMAIL)).thenReturn(Optional.of(utilisateur));
//         when(jwtService.generateToken(utilisateur)).thenReturn("access-token");

//         var response = authService.login(new LoginrequestDto(EMAIL, PASSWORD));

//         assertEquals("access-token", response.getToken());
//         assertNotEquals(PASSWORD, utilisateur.getMotDePass());
//         assertTrue(passwordEncoder.matches(PASSWORD, utilisateur.getMotDePass()));
//         verify(utilisateurRepo).save(utilisateur);
//     }

//     @Test
//     void rejectsIncorrectLegacyPasswordWithoutChangingIt() {
//         Utilisateur utilisateur = activeAdminWithPassword(PASSWORD);
//         when(utilisateurRepo.findByEmail(EMAIL)).thenReturn(Optional.of(utilisateur));

//         ResponseStatusException exception = assertThrows(
//                 ResponseStatusException.class,
//                 () -> authService.login(new LoginrequestDto(EMAIL, "incorrect"))
//         );

//         assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
//         assertEquals(PASSWORD, utilisateur.getMotDePass());
//         verify(utilisateurRepo, never()).save(utilisateur);
//     }

//     private Utilisateur activeAdminWithPassword(String password) {
//         Utilisateur utilisateur = new Utilisateur();
//         utilisateur.setId(1L);
//         utilisateur.setNom("Admin");
//         utilisateur.setPrenom("QuickOrder");
//         utilisateur.setEmail(EMAIL);
//         utilisateur.setMotDePass(password);
//         utilisateur.setRole(Role.ADMIN);
//         utilisateur.setActif(true);
//         return utilisateur;
//     }
// }
