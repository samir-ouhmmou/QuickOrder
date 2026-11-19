package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.TableRestaurant;
import QuickOrder.Restauration.Model.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRestaurantRepo extends JpaRepository<TableRestaurant,Long> {
    List<TableRestaurant> findByEtat(Etat etat);
}
