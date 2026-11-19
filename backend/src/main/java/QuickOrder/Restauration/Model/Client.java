package QuickOrder.Restauration.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
public class Client extends Utilisateur {

    private int point_achat;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Commande> commandes = new ArrayList<>();

    @Override
    public String toString() {
        return "Client{id=" + getId() + ", points=" + point_achat + "}";
    }
}
