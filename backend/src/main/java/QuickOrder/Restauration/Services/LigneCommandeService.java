package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Model.Commande;
import QuickOrder.Restauration.Model.LigneCommande;

import java.util.List;

public interface LigneCommandeService {
    Commande ajouterPlatsACommande(Long commandeId, List<LigneCommande> lignesCommande);
}
