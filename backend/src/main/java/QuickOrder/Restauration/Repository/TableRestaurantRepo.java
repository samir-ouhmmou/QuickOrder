package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.TableRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRestaurantRepo extends JpaRepository<Long, TableRestaurant> {
}
