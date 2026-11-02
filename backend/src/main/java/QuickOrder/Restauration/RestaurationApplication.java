
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
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RestaurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurationApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CategoryRepo categoryRepo, PLatRepo pLatRepo) {
		return args -> {


			if (categoryRepo.count() > 0) {
				System.out.println("Les catégories existent déjà. Initialisation ignorée.");
				return;
			}


			Categorie categorie1 = new Categorie();
			categorie1.setNom("Fast Food");
			categorie1.setDescription("Burgers, tacos et sandwichs");
			categorie1.setDisponible(true);
			categorie1.setPlats(new ArrayList<>());

			Plat plat1 = new Plat();
			plat1.setNom("Tacos");
			plat1.setDescription("Tacos au poulet, sauce fromagère et frites");
			plat1.setPrix(new BigDecimal("35.00"));
			plat1.setDisponible(true);

			Plat plat2 = new Plat();
			plat2.setNom("Cheeseburger");
			plat2.setDescription("Burger au steak haché, fromage, salade et tomate");
			plat2.setPrix(new BigDecimal("40.00"));
			plat2.setDisponible(true);

			Plat plat3 = new Plat();
			plat3.setNom("Sandwich Poulet");
			plat3.setDescription("Sandwich au poulet grillé, salade et sauce maison");
			plat3.setPrix(new BigDecimal("25.00"));
			plat3.setDisponible(true);


			Categorie categorie2 = new Categorie();
			categorie2.setNom("Pizzas");
			categorie2.setDescription("Pizzas préparées avec une pâte fraîche");
			categorie2.setDisponible(true);
			categorie2.setPlats(new ArrayList<>());

			Plat plat4 = new Plat();
			plat4.setNom("Pizza Margherita");
			plat4.setDescription("Sauce tomate, mozzarella et basilic");
			plat4.setPrix(new BigDecimal("45.00"));
			plat4.setDisponible(true);

			Plat plat5 = new Plat();
			plat5.setNom("Pizza Poulet");
			plat5.setDescription("Poulet, mozzarella, champignons et sauce tomate");
			plat5.setPrix(new BigDecimal("55.00"));
			plat5.setDisponible(true);

			Plat plat6 = new Plat();
			plat6.setNom("Pizza Végétarienne");
			plat6.setDescription("Poivrons, champignons, olives et mozzarella");
			plat6.setPrix(new BigDecimal("50.00"));
			plat6.setDisponible(true);

			Categorie categorie3 = new Categorie();
			categorie3.setNom("Salades");
			categorie3.setDescription("Salades fraîches et équilibrées");
			categorie3.setDisponible(true);
			categorie3.setPlats(new ArrayList<>());

			Plat plat7 = new Plat();
			plat7.setNom("Salade César");
			plat7.setDescription("Poulet grillé, laitue, croûtons et sauce César");
			plat7.setPrix(new BigDecimal("38.00"));
			plat7.setDisponible(true);

			Plat plat8 = new Plat();
			plat8.setNom("Salade Niçoise");
			plat8.setDescription("Thon, œufs, tomates, olives et légumes frais");
			plat8.setPrix(new BigDecimal("35.00"));
			plat8.setDisponible(true);

			Plat plat9 = new Plat();
			plat9.setNom("Salade Marocaine");
			plat9.setDescription("Tomates, concombres, oignons et persil");
			plat9.setPrix(new BigDecimal("20.00"));
			plat9.setDisponible(true);


			Categorie categorie4 = new Categorie();
			categorie4.setNom("Desserts");
			categorie4.setDescription("Desserts et douceurs maison");
			categorie4.setDisponible(true);
			categorie4.setPlats(new ArrayList<>());

			Plat plat10 = new Plat();
			plat10.setNom("Tiramisu");
			plat10.setDescription("Dessert italien au mascarpone et au café");
			plat10.setPrix(new BigDecimal("28.00"));
			plat10.setDisponible(true);

			Plat plat11 = new Plat();
			plat11.setNom("Fondant au Chocolat");
			plat11.setDescription("Gâteau au chocolat avec un cœur fondant");
			plat11.setPrix(new BigDecimal("30.00"));
			plat11.setDisponible(true);

			Plat plat12 = new Plat();
			plat12.setNom("Salade de Fruits");
			plat12.setDescription("Assortiment de fruits frais de saison");
			plat12.setPrix(new BigDecimal("22.00"));
			plat12.setDisponible(true);


			plat1.setCategorie(categorie1);
			plat2.setCategorie(categorie1);
			plat3.setCategorie(categorie1);

			categorie1.getPlats().addAll(List.of(plat1, plat2, plat3));


			plat4.setCategorie(categorie2);
			plat5.setCategorie(categorie2);
			plat6.setCategorie(categorie2);

			categorie2.getPlats().addAll(List.of(plat4, plat5, plat6));


			plat7.setCategorie(categorie3);
			plat8.setCategorie(categorie3);
			plat9.setCategorie(categorie3);

			categorie3.getPlats().addAll(List.of(plat7, plat8, plat9));

			plat10.setCategorie(categorie4);
			plat11.setCategorie(categorie4);
			plat12.setCategorie(categorie4);

			categorie4.getPlats().addAll(List.of(plat10, plat11, plat12));



			categoryRepo.saveAll(
					List.of(categorie1, categorie2, categorie3, categorie4)
			);

			pLatRepo.saveAll(
					List.of(
							plat1, plat2, plat3,
							plat4, plat5, plat6,
							plat7, plat8, plat9,
							plat10, plat11, plat12
					)
			);

			System.out.println("Initialisation terminée : 4 catégories et 12 plats créés.");
		};
	}
}
