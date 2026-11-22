package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Dto.CategoryDto;
import QuickOrder.Restauration.Exceptions.RousourceNNotFound;
import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Repository.CategoryRepo;
import QuickOrder.Restauration.mappers.Mappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategorieService implements dashBoardinterfase {
        private CategoryRepo categoryRepo;
        private Mappers mappers;
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories() {
        return categoryRepo.findAll().stream().map(mappers::fromCategory).toList();
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto request) {
        validate(request);
        Categorie category = new Categorie();
        apply(category, request);
        return mappers.fromCategory(categoryRepo.save(category));
    }

    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto request) {
        validate(request);
        Categorie category = findCategory(id);
        apply(category, request);
        return mappers.fromCategory(categoryRepo.save(category));
    }

    @Transactional
    public void deleteCategory(Long id) {
        Categorie category = findCategory(id);
        if (category.getPlats() != null && !category.getPlats().isEmpty()) {
            throw new IllegalArgumentException("Cette catégorie contient encore des plats.");
        }
        categoryRepo.delete(category);
    }

    private Categorie findCategory(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new RousourceNNotFound("Catégorie introuvable avec l'id : " + id));
    }

    private void validate(CategoryDto request) {
        if (request == null || request.getNom() == null || request.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de la catégorie est obligatoire.");
        }
    }

    private void apply(Categorie category, CategoryDto request) {
        category.setNom(request.getNom().trim());
        category.setDescription(request.getDescription() == null ? "" : request.getDescription().trim());
        category.setActive(request.getActive() == null || request.getActive());
        category.setDisponible(request.getDisponible() == null || request.getDisponible());
        category.setImageUrl(request.getImageUrl());
    }
}
