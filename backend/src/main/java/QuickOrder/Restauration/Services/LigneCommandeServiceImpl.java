package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Model.Commande;
import QuickOrder.Restauration.Model.LigneCommande;
import QuickOrder.Restauration.Model.Plat;
import QuickOrder.Restauration.Repository.CommandeRepo;
import QuickOrder.Restauration.Repository.LignecommandeRepo;
import QuickOrder.Restauration.Repository.PLatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LigneCommandeServiceImpl implements LigneCommandeService {
    private final CommandeRepo commandeRepo;
    private final LignecommandeRepo lignecommandeRepo;
    private final PLatRepo pLatRepo;

    @Override
    @Transactional
    public Commande ajouterPlatsACommande(Long commandeId, List<LigneCommande> lignesCommande) {
        if (commandeId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'identifiant de la commande est obligatoire.");
        }
        if (lignesCommande == null || lignesCommande.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La commande doit contenir au moins un plat.");
        }

        Commande commande = commandeRepo.findById(commandeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Commande introuvable : " + commandeId));
        List<LigneCommande> lignesExistantes = lignecommandeRepo.findByCommande_Id(commandeId);
        List<LigneCommande> nouvellesLignes = new ArrayList<>(lignesCommande.size());
        BigDecimal montantTotal = lignesExistantes.stream()
                .map(LigneCommande::getSousTotal)
                .filter(sousTotal -> sousTotal != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (LigneCommande ligne : lignesCommande) {
            if (ligne == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une ligne de commande est invalide.");
            }
            if (ligne.getQuantite() == null || ligne.getQuantite() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La quantité doit être supérieure à 0.");
            }
            if (ligne.getPlat() == null || ligne.getPlat().getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'identifiant du plat est obligatoire.");
            }

            Long platId = ligne.getPlat().getId();
            Plat plat = pLatRepo.findById(platId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Plat introuvable : " + platId));
            if (!Boolean.TRUE.equals(plat.getDisponible())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Le plat n'est pas disponible : " + platId);
            }
            if (plat.getPrix() == null || plat.getPrix().signum() < 0) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Le prix du plat est invalide : " + platId);
            }

            ligne.setId(null);
            ligne.setCommande(commande);
            ligne.setPlat(plat);
            ligne.setPrixUnitaire(plat.getPrix());
            montantTotal = montantTotal.add(ligne.calculerSousTotal());
            nouvellesLignes.add(ligne);
        }

        List<LigneCommande> lignesSauvegardees = lignecommandeRepo.saveAll(nouvellesLignes);
        List<LigneCommande> toutesLesLignes = new ArrayList<>(lignesExistantes);
        toutesLesLignes.addAll(lignesSauvegardees);
        commande.setLigneCommandes(toutesLesLignes);
        commande.setMontantTotal(montantTotal);
        return commandeRepo.save(commande);
    }
}
