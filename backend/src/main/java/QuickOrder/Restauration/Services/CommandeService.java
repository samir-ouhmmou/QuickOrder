package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Model.Client;
import QuickOrder.Restauration.Model.Commande;
import QuickOrder.Restauration.Model.EtatCommande;

import java.util.List;

public interface CommandeService {
    Commande creerCommande(Commande commande);

    List<Commande> consulterCommandes();

    Commande consulterCommandeParId(Long id);

    Commande modifierStatutCommande(Long id, EtatCommande nouvelEtat);

    List<Commande> consulterCommandesParUtilisateur(Long utilisateurId);

    Client verifierInformationsClient(Long clientId);
}
