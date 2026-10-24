package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementStockRepo extends JpaRepository<MouvementStock,Long> {
}
