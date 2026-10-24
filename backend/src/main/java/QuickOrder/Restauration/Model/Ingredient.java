package QuickOrder.Restauration.Model;

<<<<<<< HEAD
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
=======
import jakarta.persistence.*;
>>>>>>> Taoufik
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
=======
import java.util.List;

@Entity
>>>>>>> Taoufik
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String nom;
    private String unit;
    private int quantiteStock ;
    private int seuilAlert;
<<<<<<< HEAD
=======
    @OneToMany(mappedBy = "ingredient")
    private List<MouvementStock> mouvementStocks;
    @OneToMany(mappedBy = "ingredient")
    private List<RecetteIngredient> recetteIngredients;
>>>>>>> Taoufik
}
