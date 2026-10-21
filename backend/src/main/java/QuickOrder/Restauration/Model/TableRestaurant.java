package QuickOrder.Restauration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TableRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int numero;
    private int capacite ;
    @Enumerated(value = EnumType.STRING)
    private Etat etat;
}
