package QuickOrder.Restauration.Dto;

import java.math.BigDecimal;

public record PlatDto(
        Long id,
        String nom,
        String description,
        BigDecimal prix,
        Boolean disponible
) {
}
