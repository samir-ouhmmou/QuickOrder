package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Dto.PlatDto;

import java.util.List;

public interface platserviceinterface {

    PlatDto ajouterPlat(PlatDto platDto);

    List<PlatDto> consulterTousLesPlats();

    PlatDto consulterPlatParId(Long id);

    PlatDto modifierPlat(Long id, PlatDto platDto);

    void supprimerPlat(Long id);

    List<PlatDto> consulterPlatsParCategorie(Long categorieId);
}
