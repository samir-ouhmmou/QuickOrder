package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
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
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDepass;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private Boolean actif;
<<<<<<< HEAD
=======
    @OneToMany(mappedBy = "utilisateur")
    private List<Commande> commandes;
    @OneToMany(mappedBy = "utilisateur")
    private List<MouvementStock> mouvementStocks;

>>>>>>> Taoufik
}
