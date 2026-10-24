package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ingredient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String unit;
    private int quantiteStock;
    private int seuilAlert;

    @OneToMany(mappedBy = "ingredient")
    private List<MouvementStock> mouvementStocks;

    @OneToMany(mappedBy = "ingredient")
    private List<RecetteIngredient> recetteIngredients;
}