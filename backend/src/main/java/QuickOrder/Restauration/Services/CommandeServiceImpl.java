package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Model.Client;
import QuickOrder.Restauration.Model.Commande;
import QuickOrder.Restauration.Model.EtatCommande;
import QuickOrder.Restauration.Model.LigneCommande;
import QuickOrder.Restauration.Model.TableRestaurant;
import QuickOrder.Restauration.Repository.ClientRepo;
import QuickOrder.Restauration.Repository.CommandeRepo;
import QuickOrder.Restauration.Repository.TableRestaurantRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepo commandeRepo;
    private final ClientRepo clientRepo;
    private final TableRestaurantRepo tableRestaurantRepo;
    private final LigneCommandeService ligneCommandeService;

    @Override
    @Transactional
    public Commande creerCommande(Commande commande) {
        if (commande == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La commande est obligatoire.");
        }

        List<LigneCommande> lignesCommande = commande.getLigneCommandes();
        commande.setId(null);
        commande.setLigneCommandes(null);
        commande.setMontantTotal(BigDecimal.ZERO);
        commande.setClient(verifierInformationsClient(
                commande.getClient() == null ? null : commande.getClient().getId()));

        if (commande.getTableRestaurant() != null) {
            Long tableId = commande.getTableRestaurant().getId();
            if (tableId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'identifiant de la table est obligatoire.");
            }
            TableRestaurant table = tableRestaurantRepo.findById(tableId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Table introuvable : " + tableId));
            commande.setTableRestaurant(table);
        }

        Commande commandeEnregistree = commandeRepo.save(commande);
        if (lignesCommande != null && !lignesCommande.isEmpty()) {
            return ligneCommandeService.ajouterPlatsACommande(commandeEnregistree.getId(), lignesCommande);
        }
        return commandeEnregistree;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commande> consulterCommandes() {
        return commandeRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Commande consulterCommandeParId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'identifiant de la commande est obligatoire.");
        }
        return commandeRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Commande introuvable : " + id));
    }

    @Override
    @Transactional
    public Commande modifierStatutCommande(Long id, EtatCommande nouvelEtat) {
        if (nouvelEtat == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le statut de la commande est obligatoire.");
        }
        Commande commande = consulterCommandeParId(id);
        commande.changerEtat(nouvelEtat);
        return commandeRepo.save(commande);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commande> consulterCommandesParUtilisateur(Long utilisateurId) {
        if (utilisateurId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'identifiant de l'utilisateur est obligatoire.");
        }
        verifierInformationsClient(utilisateurId);
        return commandeRepo.findByClient_Id(utilisateurId);
    }

    @Override
    @Transactional(readOnly = true)
    public Client verifierInformationsClient(Long clientId) {
        if (clientId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'identifiant du client est obligatoire.");
        }
        return clientRepo.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Client introuvable : " + clientId));
    }
}
