package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Plat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PLatRepo extends JpaRepository<Plat,Long> {
}
