package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepo extends JpaRepository<Long, Commande> {
}
