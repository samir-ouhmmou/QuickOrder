package QuickOrder.Restauration.Model;

<<<<<<< HEAD
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modèle représentant un Mouvement de Stock selon le diagramme de classes.
 */
=======
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> Taoufik
public class MouvementStock {

    private Long id;
    private LocalDateTime dateMouvement;
    private TypeMouvement type;
    private BigDecimal quantite;
    private String motif;
<<<<<<< HEAD

    // Association: Ingredient (1) historique (0..*) MouvementStock
    private Long ingredientId;
    // private Ingredient ingredient; // À décommenter lors de la fusion avec le modèle Ingredient

    // Association: Utilisateur (1) enregistre (0..*) MouvementStock
    private Long utilisateurId;
    // private Utilisateur utilisateur; // À décommenter lors de la fusion avec le modèle Utilisateur

    // Constructeurs
    public MouvementStock() {
        this.dateMouvement = LocalDateTime.now();
        this.quantite = BigDecimal.ZERO;
    }

    public MouvementStock(Long id, LocalDateTime dateMouvement, TypeMouvement type, BigDecimal quantite, String motif) {
        this.id = id;
        this.dateMouvement = dateMouvement != null ? dateMouvement : LocalDateTime.now();
        this.type = type;
        this.quantite = quantite != null ? quantite : BigDecimal.ZERO;
        this.motif = motif;
    }

    // Méthode métier du diagramme UML
    public void enregistrer() {
        if (this.dateMouvement == null) {
            this.dateMouvement = LocalDateTime.now();
        }
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public TypeMouvement getType() {
        return type;
    }

    public void setType(TypeMouvement type) {
        this.type = type;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
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
        MouvementStock that = (MouvementStock) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MouvementStock{" +
                "id=" + id +
                ", dateMouvement=" + dateMouvement +
                ", type=" + type +
                ", quantite=" + quantite +
                ", motif='" + motif + '\'' +
                ", ingredientId=" + ingredientId +
                ", utilisateurId=" + utilisateurId +
                '}';
    }
=======
    private Long ingredientId;
    private Long utilisateurId;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "ingredient")
        private Ingredient ingredient;
>>>>>>> Taoufik
}
