package QuickOrder.Restauration.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commande {

    private Long id;
    private LocalDateTime dateCreation;
    private EtatCommande etat;
    private BigDecimal montantTotal;
    private String commentaire;
    @ManyToOne
    @JoinColumn(name = "tablerestaurant_id")
    private  TableRestaurant tableRestaurant;
    @ManyToOne
    @JoinColumn(name = "Utilisateur_id")
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "commande")
    private List<Paiement> paiements;
    @OneToMany(mappedBy = "commande")
    private List<LigneCommande> ligneCommandes;

}
