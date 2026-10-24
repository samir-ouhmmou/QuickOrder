package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    private EtatCommande etat;

    private BigDecimal montantTotal;
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "table_restaurant_id")
    private TableRestaurant tableRestaurant;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "commande")
    private List<Paiement> paiements;

    @OneToMany(mappedBy = "commande")
    private List<LigneCommande> ligneCommandes;

    @PrePersist
    public void initialiser() {
        if (dateCreation == null) {
            dateCreation = LocalDateTime.now();
        }
        if (etat == null) {
            etat = EtatCommande.EN_ATTENTE;
        }
        if (montantTotal == null) {
            montantTotal = BigDecimal.ZERO;
        }
    }

    public void ajouterPlat() {
        // Méthode métier à compléter si nécessaire.
    }

    public void ajouterPlat(Plat plat) {
        if (plat != null && plat.getPrix() != null) {
            if (montantTotal == null) {
                montantTotal = BigDecimal.ZERO;
            }
            montantTotal = montantTotal.add(plat.getPrix());
        }
    }

    public BigDecimal calculerTotal() {
        return montantTotal != null ? montantTotal : BigDecimal.ZERO;
    }

    public void changerEtat() {
        // Méthode métier à compléter si nécessaire.
    }

    public void changerEtat(EtatCommande nouvelEtat) {
        this.etat = nouvelEtat;
    }

    public void annuler() {
        this.etat = EtatCommande.ANNULEE;
    }
}