package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepo extends JpaRepository<Long, Utilisateur> {
}
