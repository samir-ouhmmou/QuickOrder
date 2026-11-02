package QuickOrder.Restauration.Repository;

import QuickOrder.Restauration.Model.Plat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PLatRepo extends JpaRepository<Plat,Long> {
    List<Plat> findByCategorie_Id(Long categoryId);
    Optional<Plat> findByCategorie_IdAndNomIgnoreCase(Long categoryId, String nom);
}
