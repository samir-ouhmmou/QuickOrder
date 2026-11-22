package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Dto.PlatDto;
import QuickOrder.Restauration.Exceptions.RousourceNNotFound;
import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Model.Plat;
import QuickOrder.Restauration.Repository.CategoryRepo;
import QuickOrder.Restauration.Repository.PLatRepo;
import QuickOrder.Restauration.mappers.Mappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class platserviceimpli implements platserviceinterface {

    private PLatRepo platRepo;
    private CategoryRepo categoryRepo;
    private Mappers mappers;

    @Override
    public PlatDto ajouterPlat(PlatDto platDto) {
        // Validations
        if (platDto.nom() == null || platDto.nom().isBlank()) {
            throw new IllegalArgumentException("Le nom du plat est obligatoire");
        }
        if (platDto.prix() == null || platDto.prix().signum() <= 0) {
            throw new IllegalArgumentException("Le prix du plat doit être supérieur à 0");
        }
        if (platDto.categorieId() == null) {
            throw new IllegalArgumentException("La catégorie du plat est obligatoire");
        }

        // Vérifier l'existence de la catégorie associée
        Categorie categorie = categoryRepo.findById(platDto.categorieId())
                .orElseThrow(() -> new RousourceNNotFound(
                        "Catégorie non trouvée avec l'id : " + platDto.categorieId()));

        Plat plat = mappers.fromPlatDto(platDto);
        plat.setId(null); // forcer la création
        plat.setCategorie(categorie);

        Plat platSauvegarde = platRepo.save(plat);
        return mappers.fromPlat(platSauvegarde);
    }

    @Override
    public List<PlatDto> consulterTousLesPlats() {
        return platRepo.findAll()
                .stream()
                .map(mappers::fromPlat)
                .toList();
    }

    @Override
    public PlatDto consulterPlatParId(Long id) {
        Plat plat = platRepo.findById(id)
                .orElseThrow(() -> new RousourceNNotFound(
                        "Plat non trouvé avec l'id : " + id));
        return mappers.fromPlat(plat);
    }

    @Override
    public PlatDto modifierPlat(Long id, PlatDto platDto) {
        // Vérifier que le plat existe
        Plat platExistant = platRepo.findById(id)
                .orElseThrow(() -> new RousourceNNotFound(
                        "Plat non trouvé avec l'id : " + id));

        // Validations
        if (platDto.nom() == null || platDto.nom().isBlank()) {
            throw new IllegalArgumentException("Le nom du plat est obligatoire");
        }
        if (platDto.prix() == null || platDto.prix().signum() <= 0) {
            throw new IllegalArgumentException("Le prix du plat doit être supérieur à 0");
        }
        if (platDto.categorieId() == null) {
            throw new IllegalArgumentException("La catégorie du plat est obligatoire");
        }

        // Vérifier l'existence de la catégorie associée
        Categorie categorie = categoryRepo.findById(platDto.categorieId())
                .orElseThrow(() -> new RousourceNNotFound(
                        "Catégorie non trouvée avec l'id : " + platDto.categorieId()));

        // Mettre à jour les champs
        platExistant.setNom(platDto.nom());
        platExistant.setDescription(platDto.description());
        platExistant.setPrix(platDto.prix());
        platExistant.setDisponible(platDto.disponible());
        platExistant.setCategorie(categorie);
        platExistant.setImageUrl(platDto.imageUrl());

        Plat platMisAJour = platRepo.save(platExistant);
        return mappers.fromPlat(platMisAJour);
    }

    @Override
    public void supprimerPlat(Long id) {
        Plat plat = platRepo.findById(id)
                .orElseThrow(() -> new RousourceNNotFound(
                        "Plat non trouvé avec l'id : " + id));
        platRepo.delete(plat);
    }

    @Override
    public List<PlatDto> consulterPlatsParCategorie(Long categorieId) {
        // Vérifier l'existence de la catégorie
        categoryRepo.findById(categorieId)
                .orElseThrow(() -> new RousourceNNotFound(
                        "Catégorie non trouvée avec l'id : " + categorieId));

        return platRepo.findByCategorie_Id(categorieId)
                .stream()
                .map(mappers::fromPlat)
                .toList();
    }
}
