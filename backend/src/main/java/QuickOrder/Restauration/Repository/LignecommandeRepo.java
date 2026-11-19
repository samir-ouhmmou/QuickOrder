package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LignecommandeRepo extends JpaRepository<LigneCommande,Long> {
    List<LigneCommande> findByCommande_Id(Long commandeId);
}
