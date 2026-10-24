package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Long, Categorie> {
}
