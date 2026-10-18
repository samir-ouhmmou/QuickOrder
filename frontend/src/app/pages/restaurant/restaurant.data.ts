import { RestaurantInfo } from './restaurant.model';

export const OSTERIA: RestaurantInfo = {
  name: 'Osteria degli Amici',
  tagline: 'Cuisine italienne faite maison, rue Mandar, Paris 2e.',
  about:
    "Osteria degli Amici est un restaurant italien du 2e arrondissement. Les pâtes, les antipasti et les desserts sont préparés sur place, avec des produits de saison, et accompagnés d'une sélection de vins italiens. La salle est petite et conviviale : la réservation est conseillée.",
  cuisines: ['Italienne', 'Méditerranéenne'],
  priceRange: '€€ - €€€',
  rating: 4.8,
  reviewCount: 64,
  address: '14 Rue Mandar, 75002 Paris',
  mapUrl: 'https://www.google.com/maps/search/?api=1&query=14+Rue+Mandar+75002+Paris',
  phone: '+33 9 85 21 54 78',
  phoneHref: 'tel:+33985215478',
  email: 'osteriadegliamiciparis@gmail.com',
  features: [
    'Réservations',
    'Terrasse',
    'Plats à emporter',
    'Convient aux végétariens',
    'Bar complet, bières et vins',
    'Wi-Fi gratuit',
    'Cartes bancaires acceptées',
  ],
  hours: [
    { day: 'Dimanche', hours: '12h00 - 22h30' },
    { day: 'Lundi', hours: '18h30 - 22h30' },
    { day: 'Mardi', hours: '12h00 - 22h30' },
    { day: 'Mercredi', hours: '12h00 - 22h30' },
    { day: 'Jeudi', hours: '12h00 - 22h30' },
    { day: 'Vendredi', hours: '12h00 - 22h30' },
    { day: 'Samedi', hours: '12h00 - 22h30' },
  ],
  menu: [
    {
      id: 'antipasti',
      label: 'Antipasti',
      // À compléter avec la carte réelle (noms et prix).
      items: [
        { name: 'Arancini', description: 'Cacio e pepe, truffe ou ragoût.' },
        { name: 'Mozzarella', description: 'Entrée à partager.' },
      ],
    },
    {
      id: 'pizze',
      label: 'Pizze',
      items: [
        { name: 'Regina', prices: [{ amount: 19 }] },
        { name: 'Calabrese' },
        { name: 'Parmigiana' },
      ],
    },
    {
      id: 'pasta',
      label: 'Pasta',
      items: [
        { name: 'Carbonara', prices: [{ amount: 19 }] },
        { name: 'Fusilli à la truffe', description: 'Crème de truffe et Parmigiano Reggiano.' },
        { name: "Pasta 'Nduja" },
        { name: 'Ragù bolognese' },
      ],
    },
    {
      id: 'dolci',
      label: 'Dolci',
      items: [
        { name: 'Cannolo sicilien' },
        { name: 'Glaces maison', description: 'Noisette, pistache, cerise amarena.' },
      ],
    },
    {
      id: 'rosati',
      label: 'Vins rosés',
      items: [
        {
          name: 'Bardolino Chiaretto Infinito DOC, Casa Santi (Veneto)',
          description: 'Frais et fruité, notes de cassis, de grenade et de griotte.',
          prices: [{ label: '15 cl', amount: 8.5 }, { label: '75 cl', amount: 37 }],
        },
        {
          name: 'Rosato Euforio Bio, Casa La Staffa (Marche)',
          description: 'Frais et fruité, notes de petits fruits rouges.',
          prices: [{ label: '15 cl', amount: 8.5 }, { label: '75 cl', amount: 39 }],
        },
        {
          name: 'Ciro Rosato Classico DOC, Casa Calabretta (Calabria)',
          description: "Frais et gourmand, délicates notes d'agrumes.",
          prices: [{ label: '15 cl', amount: 8.5 }, { label: '75 cl', amount: 38 }],
        },
      ],
    },
    {
      id: 'rossi',
      label: 'Vins rouges',
      items: [
        {
          name: 'Chianti Classico La Selvanella Riserva DOCG, Melini (Toscana)',
          description: "Ample et chaleureux, notes de framboise, de vanille, d'épices et de cuir.",
          prices: [{ label: 'Bouteille', amount: 49 }],
        },
        {
          name: "Nuhar Nero d'Avola Bio Sicilia DOC, Rapitalà (Sicilia)",
          description: 'Chaleureux et équilibré, arômes intenses de fruits mûrs.',
          prices: [{ label: '15 cl', amount: 8.5 }, { label: '75 cl', amount: 39 }],
        },
        {
          name: "Aldiano Montepulciano d'Abruzzo Riserva DOC 2019, Cantina Tollo (Abruzzo)",
          description: 'Rond et persistant, notes de fruits rouges et de balsamique.',
          prices: [{ label: '15 cl', amount: 8.5 }, { label: '75 cl', amount: 38 }],
        },
        {
          name: 'Ars Primitivo Salento IGT, Castello Monaci (Puglia)',
          description: 'Riche et velouté, notes de fruits rouges, de café torréfié et de balsamique.',
          prices: [{ label: 'Bouteille', amount: 47 }],
        },
      ],
    },
  ],
};