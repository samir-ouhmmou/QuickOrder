
package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Dto.PlatDto;
import QuickOrder.Restauration.Services.platserviceinterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plats")
@AllArgsConstructor
public class PlatController {

    private final platserviceinterface platService;

    // Ajouter un plat
    @PostMapping
    public ResponseEntity<PlatDto> ajouterPlat(
            @Valid @RequestBody PlatDto platDto) {

        PlatDto platCree = platService.ajouterPlat(platDto);

        return new ResponseEntity<>(platCree, HttpStatus.CREATED);
    }

    // Consulter tous les plats
    @GetMapping
    public ResponseEntity<List<PlatDto>> consulterTousLesPlats() {

        return ResponseEntity.ok(
                platService.consulterTousLesPlats()
        );
    }

    // Consulter un plat par son ID
    @GetMapping("/{id}")
    public ResponseEntity<PlatDto> consulterPlatParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                platService.consulterPlatParId(id)
        );
    }

    // Consulter les plats d'une catégorie
    @GetMapping("/categorie/{categorieId}")
    public ResponseEntity<List<PlatDto>> consulterPlatsParCategorie(
            @PathVariable Long categorieId) {

        return ResponseEntity.ok(
                platService.consulterPlatsParCategorie(categorieId)
        );
    }

    // Modifier un plat
    @PutMapping("/{id}")
    public ResponseEntity<PlatDto> modifierPlat(
            @PathVariable Long id,
            @Valid @RequestBody PlatDto platDto) {

        return ResponseEntity.ok(
                platService.modifierPlat(id, platDto)
        );
    }

    // Supprimer un plat
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPlat(
            @PathVariable Long id) {

        platService.supprimerPlat(id);

        return ResponseEntity.noContent().build();
    }
}
