//package QuickOrder.Restauration.Services;
//import QuickOrder.Restauration.Services.SecurityServ.JwtService;
//import QuickOrder.Restauration.Model.Commande;
//import QuickOrder.Restauration.Model.LigneCommande;
//import QuickOrder.Restauration.Model.Plat;
//import QuickOrder.Restauration.Repository.CommandeRepo;
//import QuickOrder.Restauration.Repository.LignecommandeRepo;
//import QuickOrder.Restauration.Repository.PLatRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class LigneCommandeServiceImplTest {
//    @Mock
//    private CommandeRepo commandeRepo;
//
//    @Mock
//    private LignecommandeRepo lignecommandeRepo;
//
//    @Mock
//    private PLatRepo pLatRepo;
//
//    private LigneCommandeServiceImpl service;
//
//    @BeforeEach
//    void setUp() {
//        service = new LigneCommandeServiceImpl(commandeRepo, lignecommandeRepo, pLatRepo);
//    }
//
//    @Test
//    void calculatesLinePricesAndOrderTotalFromDatabasePrices() {
//        Commande commande = new Commande();
//        commande.setId(7L);
//        Plat platUn = plat(11L, "12.50", true);
//        Plat platDeux = plat(12L, "8.00", true);
//        LigneCommande ligneUne = ligne(platUn.getId(), 2, "0.01");
//        LigneCommande ligneDeux = ligne(platDeux.getId(), 3, "999.99");
//
//        when(commandeRepo.findById(7L)).thenReturn(Optional.of(commande));
//        when(lignecommandeRepo.findByCommande_Id(7L)).thenReturn(List.of());
//        when(pLatRepo.findById(11L)).thenReturn(Optional.of(platUn));
//        when(pLatRepo.findById(12L)).thenReturn(Optional.of(platDeux));
//        when(lignecommandeRepo.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
//        when(commandeRepo.save(any(Commande.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        Commande result = service.ajouterPlatsACommande(7L, List.of(ligneUne, ligneDeux));
//
//        assertEquals(new BigDecimal("12.50"), ligneUne.getPrixUnitaire());
//        assertEquals(new BigDecimal("25.00"), ligneUne.getSousTotal());
//        assertEquals(new BigDecimal("8.00"), ligneDeux.getPrixUnitaire());
//        assertEquals(new BigDecimal("24.00"), ligneDeux.getSousTotal());
//        assertEquals(new BigDecimal("49.00"), result.getMontantTotal());
//        assertEquals(2, result.getLigneCommandes().size());
//    }
//
//    @Test
//    void rejectsNonPositiveQuantities() {
//        Commande commande = new Commande();
//        when(commandeRepo.findById(7L)).thenReturn(Optional.of(commande));
//        when(lignecommandeRepo.findByCommande_Id(7L)).thenReturn(List.of());
//
//        ResponseStatusException exception = assertThrows(
//                ResponseStatusException.class,
//                () -> service.ajouterPlatsACommande(7L, List.of(ligne(11L, 0, "4.00")))
//        );
//
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
//        verify(lignecommandeRepo, never()).saveAll(anyList());
//    }

//    @Test
//    void rejectsUnavailableDishes() {
//        Commande commande = new Commande();
//        when(commandeRepo.findById(7L)).thenReturn(Optional.of(commande));
//        when(lignecommandeRepo.findByCommande_Id(7L)).thenReturn(List.of());
//        when(pLatRepo.findById(11L)).thenReturn(Optional.of(plat(11L, "4.00", false)));
//
//        ResponseStatusException exception = assertThrows(
//                ResponseStatusException.class,
//                () -> service.ajouterPlatsACommande(7L, List.of(ligne(11L, 1, "4.00")))
//        );
//
//        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
//        verify(lignecommandeRepo, never()).saveAll(anyList());
//    }
//
//    private Plat plat(Long id, String prix, boolean disponible) {
//        Plat plat = new Plat();
//        plat.setId(id);
//        plat.setPrix(new BigDecimal(prix));
//        plat.setDisponible(disponible);
//        return plat;
//    }
//
//    private LigneCommande ligne(Long platId, int quantite, String prixEnvoye) {
//        LigneCommande ligne = new LigneCommande();
//        ligne.setPlat(plat(platId, prixEnvoye, true));
//        ligne.setQuantite(quantite);
//        ligne.setPrixUnitaire(new BigDecimal(prixEnvoye));
//        return ligne;
//    }
//}
