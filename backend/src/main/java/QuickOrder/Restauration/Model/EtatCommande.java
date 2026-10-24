package QuickOrder.Restauration.Model;

/**
 * Énumération représentant l'état d'une commande selon le diagramme de classes.
 */
public enum EtatCommande {
    EN_ATTENTE,
    EN_PREPARATION,
    PRETE,
    SERVIE,
    PAYEE,
    ANNULEE
}
