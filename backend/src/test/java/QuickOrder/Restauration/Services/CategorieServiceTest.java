package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Dto.CategoryDto;
import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Model.Plat;
import QuickOrder.Restauration.Repository.CategoryRepo;
import QuickOrder.Restauration.mappers.Mappers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategorieServiceTest {
    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private Mappers mappers;
    @InjectMocks
    private CategorieService categorieService;

    @Test
    void createsCategoryWithNormalizedNameAndVisibleDefaults() {
        CategoryDto request = new CategoryDto(null, "  Entrées  ", "  À partager  ", null, null, null, null);
        when(categoryRepo.save(any(Categorie.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mappers.fromCategory(any(Categorie.class))).thenReturn(new CategoryDto());

        categorieService.createCategory(request);

        ArgumentCaptor<Categorie> captor = ArgumentCaptor.forClass(Categorie.class);
        verify(categoryRepo).save(captor.capture());
        assertEquals("Entrées", captor.getValue().getNom());
        assertEquals("À partager", captor.getValue().getDescription());
        assertEquals(true, captor.getValue().getActive());
        assertEquals(true, captor.getValue().getDisponible());
    }

    @Test
    void doesNotDeleteCategoryThatStillContainsDishes() {
        Categorie category = new Categorie();
        category.setPlats(new ArrayList<>(List.of(new Plat())));
        when(categoryRepo.findById(4L)).thenReturn(Optional.of(category));

        assertThrows(IllegalArgumentException.class, () -> categorieService.deleteCategory(4L));

        verify(categoryRepo, never()).delete(any(Categorie.class));
    }
}
