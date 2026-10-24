package QuickOrder.Restauration.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MouvementStock {

    private Long id;
    private LocalDateTime dateMouvement;
    private TypeMouvement type;
    private BigDecimal quantite;
    private String motif;
    private Long ingredientId;
    private Long utilisateurId;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "ingredient")
        private Ingredient ingredient;
}
