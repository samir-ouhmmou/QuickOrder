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
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String description;
    private Boolean Active;
<<<<<<< HEAD
=======
    @OneToMany(mappedBy = "categorie")
    private List<Plat> plats;
>>>>>>> Taoufik

}
