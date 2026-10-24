package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouvementStockRepo extends JpaRepository<Long, MouvementStock> {
}
