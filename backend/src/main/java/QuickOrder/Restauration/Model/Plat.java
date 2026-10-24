package QuickOrder.Restauration.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plat {

    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private Boolean disponible;
    @OneToMany(mappedBy = "plat")
    private List<LigneCommande> ligneCommandes;
    @ManyToOne
    @JoinColumn(name = "plat")
    private Categorie categorie;
    @OneToMany(mappedBy = "plat")
    private List<RecetteIngredient> recetteIngredients;
}
