package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ligne_commande")
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sousTotal;

    // Commande 1 <>---- 1..* LigneCommande (composition)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    // Plat 1 ---- 0..* LigneCommande
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plat_id", nullable = false)
    private Plat plat;

    public LigneCommande() {
    }

    // ===== Méthodes métier =====

    public BigDecimal calculerSousTotal() {
        if (prixUnitaire == null || quantite == null) {
            return BigDecimal.ZERO;
        }
        this.sousTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantite));
        return this.sousTotal;
    }

    public void modifierQuantite(Integer nouvelleQuantite) {
        if (nouvelleQuantite == null || nouvelleQuantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0");
        }
        this.quantite = nouvelleQuantite;
        calculerSousTotal();
    }

    @PrePersist
    @PreUpdate
    private void avantSauvegarde() {
        calculerSousTotal();
    }

    // ===== Getters / Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public BigDecimal getSousTotal() { return sousTotal; }
    public void setSousTotal(BigDecimal sousTotal) { this.sousTotal = sousTotal; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    public Plat getPlat() { return plat; }
    public void setPlat(Plat plat) { this.plat = plat; }
}