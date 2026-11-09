package QuickOrder.Restauration;

import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Model.Client;
import QuickOrder.Restauration.Model.Plat;
import QuickOrder.Restauration.Model.Role;
import QuickOrder.Restauration.Repository.CategoryRepo;
import QuickOrder.Restauration.Repository.ClientRepo;
import QuickOrder.Restauration.Repository.PLatRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class RestaurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurationApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(
			CategoryRepo categoryRepo,
			PLatRepo platRepo, ClientRepo clientRepo, PasswordEncoder passwordEncoder) {

		return args -> {
			Client client=new Client();
			client.setPrenom("Taoufik");
			client.setNom("Mansouri");
			client.setEmail("taoufikmansouri@gmail.com");
			client.setActif(true);
			client.setRole(Role.ADMIN);
			client.setMotDePass(passwordEncoder.encode("tofik2001"));
			clientRepo.save(client);
			// Vérifier si les catégories existent déjà
			if (categoryRepo.count() > 0) {
				System.out.println("Les données existent déjà !");
				return;
			}

			// ==============================
			// 1. CRÉATION DES CATÉGORIES
			// ==============================

			Categorie entree = new Categorie();
			entree.setNom("Entrées");
			entree.setDescription("Entrées marocaines et salades");
			entree.setActive(true);
			entree.setDisponible(true);

			Categorie platPrincipal = new Categorie();
			platPrincipal.setNom("Plats principaux");
			platPrincipal.setDescription("Plats traditionnels marocains");
			platPrincipal.setActive(true);
			platPrincipal.setDisponible(true);

			Categorie dessert = new Categorie();
			dessert.setNom("Desserts");
			dessert.setDescription("Desserts marocains");
			dessert.setActive(true);
			dessert.setDisponible(true);

			Categorie boisson = new Categorie();
			boisson.setNom("Boissons");
			boisson.setDescription("Boissons chaudes et froides");
			boisson.setActive(true);
			boisson.setDisponible(true);

			List<Categorie> categories = categoryRepo.saveAll(
					List.of(entree, platPrincipal, dessert, boisson)
			);

			// ==============================
			// 2. CRÉATION DES PLATS
			// ==============================

			Plat saladeMarocaine = new Plat();
			saladeMarocaine.setNom("Salade marocaine");
			saladeMarocaine.setDescription("Tomates, concombres, oignons et huile d'olive");
			saladeMarocaine.setPrix(new BigDecimal("25.00"));
			saladeMarocaine.setDisponible(true);
			saladeMarocaine.setCategorie(entree);

			Plat briouate = new Plat();
			briouate.setNom("Briouates au fromage");
			briouate.setDescription("Feuilles de brick croustillantes au fromage");
			briouate.setPrix(new BigDecimal("30.00"));
			briouate.setDisponible(true);
			briouate.setCategorie(entree);

			Plat tajinePoulet = new Plat();
			tajinePoulet.setNom("Tajine de poulet");
			tajinePoulet.setDescription("Poulet aux olives et citron confit");
			tajinePoulet.setPrix(new BigDecimal("65.00"));
			tajinePoulet.setDisponible(true);
			tajinePoulet.setCategorie(platPrincipal);

			Plat couscous = new Plat();
			couscous.setNom("Couscous marocain");
			couscous.setDescription("Couscous aux légumes et viande");
			couscous.setPrix(new BigDecimal("70.00"));
			couscous.setDisponible(true);
			couscous.setCategorie(platPrincipal);

			Plat pastilla = new Plat();
			pastilla.setNom("Pastilla au poulet");
			pastilla.setDescription("Pastilla traditionnelle aux amandes");
			pastilla.setPrix(new BigDecimal("75.00"));
			pastilla.setDisponible(true);
			pastilla.setCategorie(platPrincipal);

			Plat chebakia = new Plat();
			chebakia.setNom("Chebakia");
			chebakia.setDescription("Pâtisserie marocaine au miel et sésame");
			chebakia.setPrix(new BigDecimal("20.00"));
			chebakia.setDisponible(true);
			chebakia.setCategorie(dessert);

			Plat the = new Plat();
			the.setNom("Thé à la menthe");
			the.setDescription("Thé marocain traditionnel à la menthe");
			the.setPrix(new BigDecimal("12.00"));
			the.setDisponible(true);
			the.setCategorie(boisson);

			Plat jusOrange = new Plat();
			jusOrange.setNom("Jus d'orange");
			jusOrange.setDescription("Jus d'orange frais");
			jusOrange.setPrix(new BigDecimal("15.00"));
			jusOrange.setDisponible(true);
			jusOrange.setCategorie(boisson);

			platRepo.saveAll(List.of(
					saladeMarocaine,
					briouate,
					tajinePoulet,
					couscous,
					pastilla,
					chebakia,
					the,
					jusOrange
			));

			System.out.println("==================================");
			System.out.println("Données initiales insérées !");
			System.out.println("Catégories : " + categoryRepo.count());
			System.out.println("Plats : " + platRepo.count());
			System.out.println("==================================");
		};
	}
}
