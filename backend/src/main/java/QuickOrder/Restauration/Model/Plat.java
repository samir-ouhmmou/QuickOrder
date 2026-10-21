package QuickOrder.Restauration.Model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Modèle représentant un Plat selon le diagramme de classes.
 */
public class Plat {

    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private Boolean disponible;

    // Association: Categorie (1) contient (0..*) Plat
    private Long categorieId;
    // private Categorie categorie; // À décommenter lors de la fusion avec le modèle Categorie

    // Constructeurs
    public Plat() {
        this.disponible = true;
    }

    public Plat(Long id, String nom, String description, BigDecimal prix, Boolean disponible) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.disponible = disponible;
    }

    // Méthodes métier du diagramme UML
    public void modifierPrix() {
        // Méthode sans paramètre définie dans le diagramme
    }

    public void modifierPrix(BigDecimal nouveauPrix) {
        this.prix = nouveauPrix;
    }

    public void changerDisponibilite() {
        this.disponible = (this.disponible == null) ? true : !this.disponible;
    }

    public void changerDisponibilite(Boolean disponible) {
        this.disponible = disponible;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plat plat = (Plat) o;
        return Objects.equals(id, plat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Plat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", disponible=" + disponible +
                ", categorieId=" + categorieId +
                '}';
    }
}
