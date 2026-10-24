package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngrediantRepositoryRepo extends JpaRepository< Ingredient,Long> {
}
