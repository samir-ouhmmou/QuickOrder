package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client,Long> {
}
