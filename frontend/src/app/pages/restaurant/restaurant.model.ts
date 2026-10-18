export interface PriceOption {
  label?: string;
  amount: number;
}

export interface MenuItem {
  name: string;
  description?: string;
  prices?: PriceOption[];
}

export interface MenuCategory {
  id: string;
  label: string;
  items: MenuItem[];
}

export interface OpeningHours {
  day: string;
  hours: string;
}

export interface RestaurantInfo {
  name: string;
  tagline: string;
  about: string;
  cuisines: string[];
  priceRange: string;
  rating: number;
  reviewCount: number;
  address: string;
  mapUrl: string;
  phone: string;
  phoneHref: string;
  email: string;
  features: string[];
  /** Indexé comme Date.getDay() : 0 = dimanche. */
  hours: OpeningHours[];
  menu: MenuCategory[];
}