package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mouvement_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MouvementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateMouvement;

    @Enumerated(EnumType.STRING)
    private TypeMouvement type;

    private BigDecimal quantite;
    private String motif;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @PrePersist
    public void initialiser() {
        if (dateMouvement == null) {
            dateMouvement = LocalDateTime.now();
        }
        if (quantite == null) {
            quantite = BigDecimal.ZERO;
        }
    }

    public void enregistrer() {
        if (dateMouvement == null) {
            dateMouvement = LocalDateTime.now();
        }
    }
}