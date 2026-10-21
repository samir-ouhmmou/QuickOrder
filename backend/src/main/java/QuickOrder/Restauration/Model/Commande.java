package QuickOrder.Restauration.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modèle représentant une Commande selon le diagramme de classes.
 */
public class Commande {

    private Long id;
    private LocalDateTime dateCreation;
    private EtatCommande etat;
    private BigDecimal montantTotal;
    private String commentaire;

    // Association: TableRestaurant (1) accueille (0..*) Commande
    private Long tableRestaurantId;
    // private TableRestaurant tableRestaurant; // À décommenter lors de la fusion avec le modèle TableRestaurant

    // Association: Utilisateur (1) crée (0..*) Commande
    private Long utilisateurId;

    // Constructeurs
    public Commande() {
        this.dateCreation = LocalDateTime.now();
        this.etat = EtatCommande.EN_ATTENTE;
        this.montantTotal = BigDecimal.ZERO;
    }

    public Commande(Long id, LocalDateTime dateCreation, EtatCommande etat, BigDecimal montantTotal, String commentaire) {
        this.id = id;
        this.dateCreation = dateCreation != null ? dateCreation : LocalDateTime.now();
        this.etat = etat != null ? etat : EtatCommande.EN_ATTENTE;
        this.montantTotal = montantTotal != null ? montantTotal : BigDecimal.ZERO;
        this.commentaire = commentaire;
    }

    // Méthodes métier du diagramme UML
    public void ajouterPlat() {
        // Méthode sans paramètre définie dans le diagramme
    }

    public void ajouterPlat(Plat plat) {
        if (plat != null && plat.getPrix() != null) {
            this.montantTotal = this.montantTotal.add(plat.getPrix());
        }
    }

    public BigDecimal calculerTotal() {
        return this.montantTotal != null ? this.montantTotal : BigDecimal.ZERO;
    }

    public void changerEtat() {
        // Méthode sans paramètre définie dans le diagramme
    }

    public void changerEtat(EtatCommande nouvelEtat) {
        this.etat = nouvelEtat;
    }

    public void annuler() {
        this.etat = EtatCommande.ANNULEE;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getTableRestaurantId() {
        return tableRestaurantId;
    }

    public void setTableRestaurantId(Long tableRestaurantId) {
        this.tableRestaurantId = tableRestaurantId;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return Objects.equals(id, commande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", dateCreation=" + dateCreation +
                ", etat=" + etat +
                ", montantTotal=" + montantTotal +
                ", commentaire='" + commentaire + '\'' +
                ", tableRestaurantId=" + tableRestaurantId +
                ", utilisateurId=" + utilisateurId +
                '}';
    }
}
