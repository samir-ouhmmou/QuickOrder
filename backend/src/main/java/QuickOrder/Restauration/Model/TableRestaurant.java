package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
@NoArgsConstructor
@AllArgsConstructor
@Data
=======
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> Taoufik
public class TableRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int numero;
    private int capacite ;
    @Enumerated(value = EnumType.STRING)
    private Etat etat;
<<<<<<< HEAD
    private List<Commande> commandes;
=======
    @OneToMany(mappedBy = "tableRestaurant")
    private List<Commande> commandes;

>>>>>>> Taoufik
}
