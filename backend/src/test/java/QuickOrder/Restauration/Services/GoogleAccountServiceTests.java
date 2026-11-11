//package QuickOrder.Restauration.Services;
//
//import QuickOrder.Restauration.Model.Client;
//import QuickOrder.Restauration.Model.Role;
//import QuickOrder.Restauration.Repository.ClientRepo;
//import QuickOrder.Restauration.Repository.UtilisateurRepo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class GoogleAccountServiceTests {
//    @Mock
//    private UtilisateurRepo utilisateurRepo;
//    @Mock
//    private ClientRepo clientRepo;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private JwtService jwtService;
//    @Mock
//    private OAuth2User googleUser;
//
//    @InjectMocks
//    private GoogleAccountService googleAccountService;
//
//    @Test
//    void createsAClientForANewVerifiedGoogleAccount() {
//        when(googleUser.getAttribute("email")).thenReturn("Test@Example.com");
//        when(googleUser.getAttribute("email_verified")).thenReturn(true);
//        when(googleUser.getAttribute("given_name")).thenReturn("Test");
//        when(googleUser.getAttribute("family_name")).thenReturn("Client");
//        when(utilisateurRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
//        when(passwordEncoder.encode(anyString())).thenReturn("encoded-random-password");
//        when(clientRepo.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
//        when(jwtService.generateToken(any())).thenReturn("quickorder-token");
//
//        assertEquals("quickorder-token", googleAccountService.authenticate(googleUser));
//
//        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
//        verify(clientRepo).save(clientCaptor.capture());
//        Client created = clientCaptor.getValue();
//        assertEquals("test@example.com", created.getEmail());
//        assertEquals("Test", created.getPrenom());
//        assertEquals("Client", created.getNom());
//        assertEquals(Role.CLIENT, created.getRole());
//        assertEquals(true, created.getActif());
//        assertEquals("encoded-random-password", created.getMotDePass());
//    }
//
//    @Test
//    void rejectsGoogleAccountsWithoutVerifiedEmail() {
//        when(googleUser.getAttribute("email")).thenReturn("test@example.com");
//        when(googleUser.getAttribute("email_verified")).thenReturn(false);
//
//        assertThrows(
//                OAuth2AuthenticationException.class,
//                () -> googleAccountService.authenticate(googleUser)
//        );
//    }
//}
