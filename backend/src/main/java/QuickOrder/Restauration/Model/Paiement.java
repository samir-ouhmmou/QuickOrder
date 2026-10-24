package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
<<<<<<< HEAD
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

>>>>>>> Taoufik
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
<<<<<<< HEAD
@Table(name = "paiement")
=======
@Data
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> Taoufik
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime datePaiement;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModePaiement mode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatPaiement etat = EtatPaiement.EN_ATTENTE;

    private String reference;

<<<<<<< HEAD
    // Commande 1 ---- 0..* Paiement
=======

>>>>>>> Taoufik
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

<<<<<<< HEAD
    public Paiement() {
    }

    // ===== Méthodes métier =====

    public void enregistrer() {
        this.datePaiement = LocalDateTime.now();
        this.etat = EtatPaiement.ACCEPTE;
    }

    public void rembourser() {
        if (this.etat != EtatPaiement.ACCEPTE) {
            throw new IllegalStateException("Seul un paiement accepté peut être remboursé");
        }
        this.etat = EtatPaiement.REMBOURSE;
    }

    // ===== Getters / Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public ModePaiement getMode() { return mode; }
    public void setMode(ModePaiement mode) { this.mode = mode; }

    public EtatPaiement getEtat() { return etat; }
    public void setEtat(EtatPaiement etat) { this.etat = etat; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
=======

>>>>>>> Taoufik
}
