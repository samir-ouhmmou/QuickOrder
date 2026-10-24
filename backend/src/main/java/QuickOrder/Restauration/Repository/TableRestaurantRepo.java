package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.TableRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRestaurantRepo extends JpaRepository<TableRestaurant,Long> {
}
