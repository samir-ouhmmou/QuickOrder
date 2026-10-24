package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Categorie,Long> {
}
