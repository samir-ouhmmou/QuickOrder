
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


}
