package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
<<<<<<< HEAD
import java.math.BigDecimal;

@Entity
@Table(
    name = "recette_ingredient",
    uniqueConstraints = @UniqueConstraint(columnNames = {"plat_id", "ingredient_id"})
)
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> Taoufik
public class RecetteIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantiteNecessaire;

<<<<<<< HEAD
    // Plat 1 ---- 0..* RecetteIngredient
=======

>>>>>>> Taoufik
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plat_id", nullable = false)
    private Plat plat;

<<<<<<< HEAD
    // Ingredient 1 ---- 0..* RecetteIngredient
=======

>>>>>>> Taoufik
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

<<<<<<< HEAD
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
=======



>>>>>>> Taoufik
}
