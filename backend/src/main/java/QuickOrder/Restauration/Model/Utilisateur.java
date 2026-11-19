package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String motDePass;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean actif;


    @OneToMany(mappedBy = "utilisateur")
    private List<MouvementStock> mouvementStocks;
}