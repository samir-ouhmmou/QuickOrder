package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Dto.LoginResponseDto;
import QuickOrder.Restauration.Dto.LoginrequestDto;
import QuickOrder.Restauration.Model.Utilisateur;
import QuickOrder.Restauration.Repository.UtilisateurRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
@AllArgsConstructor
public class AuthService {
    private final UtilisateurRepo utilisateurRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
public LoginResponseDto login(LoginrequestDto loginrequestDto){
    Utilisateur utilisateur=utilisateurRepo.findByEmail(loginrequestDto.getEmail()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect"));
      if(!Boolean.TRUE.equals(utilisateur.getActif())){
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect");
      }
      if(!passwordMatches(utilisateur, loginrequestDto.getPassword())){
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect");
      }
      String token=jwtService.generateToken(utilisateur);
    return new LoginResponseDto(
            token,
            "Bearer",
            utilisateur.getId(),
            utilisateur.getNom(),
            utilisateur.getPrenom(),
            utilisateur.getEmail(),
            utilisateur.getRole()
    );
   }

    private boolean passwordMatches(Utilisateur utilisateur, String rawPassword) {
        String storedPassword = utilisateur.getMotDePass();
        if (storedPassword == null) {
            return false;
        }

        if (storedPassword.matches("^\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}$")) {
            return passwordEncoder.matches(rawPassword, storedPassword);
        }

        if (!MessageDigest.isEqual(
                rawPassword.getBytes(StandardCharsets.UTF_8),
                storedPassword.getBytes(StandardCharsets.UTF_8)
        )) {
            return false;
        }

        utilisateur.setMotDePass(passwordEncoder.encode(rawPassword));
        utilisateurRepo.save(utilisateur);
        return true;
    }
}
