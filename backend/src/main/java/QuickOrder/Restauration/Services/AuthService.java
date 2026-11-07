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
      if(!passwordEncoder.matches(loginrequestDto.getPassword(),utilisateur.getMotDePass())){
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
}
