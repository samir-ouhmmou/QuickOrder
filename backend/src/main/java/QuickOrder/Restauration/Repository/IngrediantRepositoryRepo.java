package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngrediantRepositoryRepo extends JpaRepository<Long, Ingredient> {
}
