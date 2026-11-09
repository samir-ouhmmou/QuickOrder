package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LignecommandeRepo extends JpaRepository<LigneCommande,Long> {
}
