package QuickOrder.Restauration.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categorie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
<<<<<<< HEAD
    private Boolean active;

=======
    private Boolean disponible;
     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
>>>>>>> 5061628b15148466b8cb7b2599c1d92c77c215b7
    @OneToMany(mappedBy = "categorie")
    private List<Plat> plats;
}