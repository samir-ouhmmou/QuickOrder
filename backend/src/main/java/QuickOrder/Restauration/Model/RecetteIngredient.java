package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
    name = "recette_ingredient",
    uniqueConstraints = @UniqueConstraint(columnNames = {"plat_id", "ingredient_id"})
)
public class RecetteIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantiteNecessaire;

    // Plat 1 ---- 0..* RecetteIngredient
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plat_id", nullable = false)
    private Plat plat;

    // Ingredient 1 ---- 0..* RecetteIngredient
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public RecetteIngredient() {
    }

    // ===== Getters / Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getQuantiteNecessaire() { return quantiteNecessaire; }
    public void setQuantiteNecessaire(BigDecimal quantiteNecessaire) { this.quantiteNecessaire = quantiteNecessaire; }

    public Plat getPlat() { return plat; }
    public void setPlat(Plat plat) { this.plat = plat; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }
}
