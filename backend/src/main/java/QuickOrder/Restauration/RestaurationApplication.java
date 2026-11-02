
		package QuickOrder.Restauration;

import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Model.Plat;
import QuickOrder.Restauration.Repository.CategoryRepo;
import QuickOrder.Restauration.Repository.PLatRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class RestaurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurationApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CategoryRepo categoryRepo, PLatRepo pLatRepo) {
		return args -> {
			List<Categorie> categories = categoryRepo.findAll();
			int platsAjoutes = 0;

			for (CategorySeed categorySeed : menuParCategorie()) {
				Categorie category = categories.stream()
						.filter(existing -> existing.getNom() != null
								&& existing.getNom().equalsIgnoreCase(categorySeed.nom()))
						.findFirst()
						.orElseGet(() -> {
							Categorie nouvelleCategorie = new Categorie();
							nouvelleCategorie.setNom(categorySeed.nom());
							nouvelleCategorie.setDescription(categorySeed.description());
							nouvelleCategorie.setActive(true);
							nouvelleCategorie.setDisponible(true);
							Categorie saved = categoryRepo.save(nouvelleCategorie);
							categories.add(saved);
							return saved;
						});

				for (PlatSeed platSeed : categorySeed.plats()) {
					boolean existeDeja = pLatRepo
							.findByCategorie_IdAndNomIgnoreCase(category.getId(), platSeed.nom())
							.isPresent();
					if (existeDeja) {
						continue;
					}

					Plat plat = new Plat();
					plat.setNom(platSeed.nom());
					plat.setDescription(platSeed.description());
					plat.setPrix(platSeed.prix());
					plat.setDisponible(true);
					plat.setCategorie(category);
					pLatRepo.save(plat);
					platsAjoutes++;
				}
			}

			System.out.println("Initialisation terminée : " + platsAjoutes + " plats ajoutés aux catégories.");
		};
	}

	private List<CategorySeed> menuParCategorie() {
		return List.of(
				new CategorySeed("Fast Food", "Burgers, tacos et sandwichs", List.of(
						new PlatSeed("Tacos", "Tacos au poulet, sauce fromagère et frites", new BigDecimal("35.00")),
						new PlatSeed("Cheeseburger", "Burger au steak haché, fromage, salade et tomate", new BigDecimal("40.00")),
						new PlatSeed("Sandwich Poulet", "Sandwich au poulet grillé, salade et sauce maison", new BigDecimal("25.00"))
				)),
				new CategorySeed("Pizzas", "Pizzas préparées avec une pâte fraîche", List.of(
						new PlatSeed("Pizza Margherita", "Sauce tomate, mozzarella et basilic", new BigDecimal("45.00")),
						new PlatSeed("Pizza Poulet", "Poulet, mozzarella, champignons et sauce tomate", new BigDecimal("55.00")),
						new PlatSeed("Pizza Végétarienne", "Poivrons, champignons, olives et mozzarella", new BigDecimal("50.00"))
				)),
				new CategorySeed("Salades", "Salades fraîches et équilibrées", List.of(
						new PlatSeed("Salade César", "Poulet grillé, laitue, croûtons et sauce César", new BigDecimal("38.00")),
						new PlatSeed("Salade Niçoise", "Thon, œufs, tomates, olives et légumes frais", new BigDecimal("35.00")),
						new PlatSeed("Salade Marocaine", "Tomates, concombres, oignons et persil", new BigDecimal("20.00"))
				)),
				new CategorySeed("Desserts", "Desserts et douceurs maison", List.of(
						new PlatSeed("Tiramisu", "Dessert italien au mascarpone et au café", new BigDecimal("28.00")),
						new PlatSeed("Fondant au Chocolat", "Gâteau au chocolat avec un cœur fondant", new BigDecimal("30.00")),
						new PlatSeed("Salade de Fruits", "Assortiment de fruits frais de saison", new BigDecimal("22.00"))
				))
		);
	}

	private record CategorySeed(String nom, String description, List<PlatSeed> plats) {
	}

	private record PlatSeed(String nom, String description, BigDecimal prix) {
	}
}
