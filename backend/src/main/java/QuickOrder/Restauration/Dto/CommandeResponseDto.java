package QuickOrder.Restauration.Dto;

import QuickOrder.Restauration.Model.EtatCommande;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CommandeResponseDto(
        Long id,
        LocalDateTime dateCreation,
        EtatCommande etat,
        BigDecimal montantTotal
) {
}
