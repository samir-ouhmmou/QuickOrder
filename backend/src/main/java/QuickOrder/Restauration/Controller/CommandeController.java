package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Dto.CommandeResponseDto;
import QuickOrder.Restauration.Model.Client;
import QuickOrder.Restauration.Model.Commande;
import QuickOrder.Restauration.Model.Role;
import QuickOrder.Restauration.Model.Utilisateur;
import QuickOrder.Restauration.Repository.UtilisateurRepo;
import QuickOrder.Restauration.Services.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commande")
@RequiredArgsConstructor
public class CommandeController {
    private final CommandeService commandeService;
    private final UtilisateurRepo utilisateurRepo;

    @PostMapping
    public ResponseEntity<CommandeResponseDto> creerCommande(
            Authentication authentication,
            @RequestBody Commande commande
    ) {
        if (commande == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La commande est obligatoire.");
        }
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Veuillez vous connecter pour commander.");
        }

        Utilisateur utilisateur = utilisateurRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur introuvable."));
        if (!(utilisateur instanceof Client client) || client.getRole() != Role.CLIENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul un client peut créer une commande.");
        }

        commande.setClient(client);
        Commande commandeCreee = commandeService.creerCommande(commande);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommandeResponseDto(
                commandeCreee.getId(),
                commandeCreee.getDateCreation(),
                commandeCreee.getEtat(),
                commandeCreee.getMontantTotal()
        ));
    }
}
