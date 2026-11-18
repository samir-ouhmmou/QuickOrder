package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepo extends JpaRepository<Commande,Long> {
    List<Commande> findByClient_Id(Long clientId);
}
