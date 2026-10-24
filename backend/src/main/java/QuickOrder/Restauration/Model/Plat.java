package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "plat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private BigDecimal prix;

    private Boolean disponible = true;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "plat")
    private List<LigneCommande> ligneCommandes;

    @OneToMany(mappedBy = "plat")
    private List<RecetteIngredient> recetteIngredients;

    public void modifierPrix() {
        // Méthode métier à compléter si nécessaire.
    }

    public void modifierPrix(BigDecimal nouveauPrix) {
        if (nouveauPrix == null || nouveauPrix.signum() < 0) {
            throw new IllegalArgumentException(
                    "Le prix ne peut pas être négatif ou nul"
            );
        }
        prix = nouveauPrix;
    }

    public void changerDisponibilite() {
        disponible = disponible == null || !disponible;
    }

    public void changerDisponibilite(Boolean disponible) {
        this.disponible = disponible;
    }

    @PrePersist
    public void initialiser() {
        if (disponible == null) {
            disponible = true;
        }
    }
}