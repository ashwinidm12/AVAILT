package com.availt;

import com.availt.model.Menu;
import com.availt.model.ServiceEntity;
import com.availt.repository.MenuRepository;
import com.availt.repository.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ServiceRepository serviceRepository;
    private final MenuRepository menuRepository;

    public DataInitializer(ServiceRepository serviceRepository, MenuRepository menuRepository) {
        this.serviceRepository = serviceRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void run(String... args) {
        if (serviceRepository.count() > 0) {
            return;
        }

        ServiceEntity catering = new ServiceEntity();
        catering.setName("Premium Catering Co.");
        catering.setCategory("Catering");
        catering.setType("Food & Beverage");
        catering.setAddress("Downtown Avenue 12, City Central");
        catering.setContact("+91 98765 43210");
        catering.setRating(4.9);
        catering.setPrice(1200.0);
        catering.setImageUrl("https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=900&q=80");
        catering.setDescription("Weddings, corporate lunches, and live counters with chef-led service.");
        saveSeed(catering);
        seedCateringMenus(catering.getId());

        ServiceEntity venue = new ServiceEntity();
        venue.setName("Grand Banquet Hall");
        venue.setCategory("Venue Booking");
        venue.setType("Banquet Hall");
        venue.setAddress("Sunrise Road 5, Business Park");
        venue.setContact("+91 91234 56789");
        venue.setRating(4.8);
        venue.setPrice(9500.0);
        venue.setImageUrl("https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=900&q=80");
        venue.setDescription("AC halls, valet parking, and in-house AV for up to 800 guests.");
        saveSeed(venue);

        ServiceEntity event = new ServiceEntity();
        event.setName("EventSpark Management");
        event.setCategory("Event Management");
        event.setType("Corporate & Social");
        event.setAddress("Maple Street 88, Midtown");
        event.setContact("+91 99876 54321");
        event.setRating(4.7);
        event.setPrice(7000.0);
        event.setImageUrl("https://images.unsplash.com/photo-1521737604893-d14cc237f11d?auto=format&fit=crop&w=900&q=80");
        event.setDescription("End-to-end decor, artists, and stage production for launches and weddings.");
        saveSeed(event);

        ServiceEntity photo = new ServiceEntity();
        photo.setName("FocusFrames Photography");
        photo.setCategory("Photographer");
        photo.setType("Event Photography");
        photo.setAddress("Cedar Lane 20, Art District");
        photo.setContact("+91 90123 45678");
        photo.setRating(4.6);
        photo.setPrice(4500.0);
        photo.setImageUrl("https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=900&q=80");
        photo.setDescription("Candid wedding, pre-wedding, and commercial shoots with drone add-ons.");
        saveSeed(photo);

        ServiceEntity medical = new ServiceEntity();
        medical.setName("QuickCare Medical");
        medical.setCategory("Medical Services");
        medical.setType("Home Doctor Visit");
        medical.setAddress("Oak Avenue 32, Health Zone");
        medical.setContact("+91 93456 78901");
        medical.setRating(4.4);
        medical.setPrice(1800.0);
        medical.setImageUrl("https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?auto=format&fit=crop&w=900&q=80");
        medical.setDescription("Certified home visits, tele-consults, and sample collection.");
        saveSeed(medical);

        ServiceEntity blood = new ServiceEntity();
        blood.setName("LifeStream Blood Donors");
        blood.setCategory("Blood Donors");
        blood.setType("Emergency Blood Supply");
        blood.setAddress("River Road 6, Medical Plaza");
        blood.setContact("+91 91234 98765");
        blood.setRating(4.5);
        blood.setPrice(2500.0);
        blood.setImageUrl("https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?auto=format&fit=crop&w=900&q=80");
        blood.setDescription("Verified donor pools and hospital tie-ups for urgent needs.");
        saveSeed(blood);

        ServiceEntity babyCare = new ServiceEntity();
        babyCare.setName("Little Steps Baby Care");
        babyCare.setCategory("Baby Care");
        babyCare.setType("Nanny & Daycare");
        babyCare.setAddress("Clover Street 18, Garden View");
        babyCare.setContact("+91 92345 67890");
        babyCare.setRating(4.6);
        babyCare.setPrice(3000.0);
        babyCare.setImageUrl("https://images.unsplash.com/photo-1509099836639-18ba3cb0f4d6?auto=format&fit=crop&w=900&q=80");
        babyCare.setDescription("Trusted nannies, daycare slots, and night nurses.");
        saveSeed(babyCare);

        ServiceEntity tutoring = new ServiceEntity();
        tutoring.setName("BrightMinds Tutoring");
        tutoring.setCategory("Tutoring");
        tutoring.setType("Academic Coaching");
        tutoring.setAddress("Elm Street 44, Study Zone");
        tutoring.setContact("+91 98712 34567");
        tutoring.setRating(4.5);
        tutoring.setPrice(2200.0);
        tutoring.setImageUrl("https://images.unsplash.com/photo-1516979187457-637abb4f9353?auto=format&fit=crop&w=900&q=80");
        tutoring.setDescription("JEE, NEET, languages, and small-group online batches.");
        saveSeed(tutoring);

        ServiceEntity petStore = new ServiceEntity();
        petStore.setName("Happy Paws Pet Store");
        petStore.setCategory("Pet Store");
        petStore.setType("Pet Supplies");
        petStore.setAddress("Pine Street 12, Pet Plaza");
        petStore.setContact("+91 94678 12345");
        petStore.setRating(4.3);
        petStore.setPrice(1500.0);
        petStore.setImageUrl("https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=900&q=80");
        petStore.setDescription("Premium pet food, grooming slots, and same-day delivery.");
        saveSeed(petStore);

        ServiceEntity farmers = new ServiceEntity();
        farmers.setName("GreenHarvest Farmer Services");
        farmers.setCategory("Farmers Services");
        farmers.setType("Organic Farming");
        farmers.setAddress("Harvest Road 2, Agro Park");
        farmers.setContact("+91 95678 32109");
        farmers.setRating(4.2);
        farmers.setPrice(2800.0);
        farmers.setImageUrl("https://images.unsplash.com/photo-1501004318641-b39e6451bec6?auto=format&fit=crop&w=900&q=80");
        farmers.setDescription("Soil testing, crop advisory, and harvest labour coordination.");
        saveSeed(farmers);

        ServiceEntity grocery = new ServiceEntity();
        grocery.setName("FreshCart Grocery Store");
        grocery.setCategory("Grocery Store");
        grocery.setType("Home Delivery");
        grocery.setAddress("Market Street 10, Green Plaza");
        grocery.setContact("+91 96543 21098");
        grocery.setRating(4.7);
        grocery.setPrice(999.0);
        grocery.setImageUrl("https://images.unsplash.com/photo-1523986371872-9d3ba2e2f6a7?auto=format&fit=crop&w=900&q=80");
        grocery.setDescription("Fresh produce, staples, and scheduled doorstep delivery.");
        saveSeed(grocery);

        ServiceEntity boutique = new ServiceEntity();
        boutique.setName("StyleNest Boutique");
        boutique.setCategory("Boutique");
        boutique.setType("Fashion & Design");
        boutique.setAddress("Fashion Lane 7, Mall Street");
        boutique.setContact("+91 94567 89012");
        boutique.setRating(4.6);
        boutique.setPrice(2700.0);
        boutique.setImageUrl("https://images.unsplash.com/photo-1495121605193-b116b5b9c5d1?auto=format&fit=crop&w=900&q=80");
        boutique.setDescription("Designer wear, tailoring, and personal styling sessions.");
        saveSeed(boutique);

        ServiceEntity bookMart = new ServiceEntity();
        bookMart.setName("PageTurner Book Mart");
        bookMart.setCategory("Book Mart");
        bookMart.setType("Book Delivery");
        bookMart.setAddress("Library Road 9, Book Plaza");
        bookMart.setContact("+91 91234 65789");
        bookMart.setRating(4.8);
        bookMart.setPrice(450.0);
        bookMart.setImageUrl("https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=900&q=80");
        bookMart.setDescription("Academic titles, competitive exam sets, and quick delivery.");
        saveSeed(bookMart);

        ServiceEntity homeService = new ServiceEntity();
        homeService.setName("HomeCare House Services");
        homeService.setCategory("House Service");
        homeService.setType("Cleaning & Repair");
        homeService.setAddress("Oakwood Avenue 11, Home Zone");
        homeService.setContact("+91 98765 12345");
        homeService.setRating(4.5);
        homeService.setPrice(1800.0);
        homeService.setImageUrl("https://images.unsplash.com/photo-1494526585095-c41746248156?auto=format&fit=crop&w=900&q=80");
        homeService.setDescription("Deep cleaning, repairs, and pest control with verified staff.");
        saveSeed(homeService);
    }

    private void saveSeed(ServiceEntity entity) {
        entity.setCommunitySubmitted(Boolean.FALSE);
        serviceRepository.save(entity);
    }

    private void seedCateringMenus(Long serviceId) {
        Object[][] rows = new Object[][]{
                {"South Indian", "Idli, dosa, pongal, sambar, chutneys, kesari", 1200.0,
                        "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=900&q=80"},
                {"North Indian", "Paneer butter masala, dal, jeera rice, naan, dessert", 1400.0,
                        "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=900&q=80"},
                {"Breakfast", "Poha, upma, sandwiches, tea, coffee", 700.0,
                        "https://images.unsplash.com/photo-1533089860892-a7c6f0a88666?auto=format&fit=crop&w=900&q=80"},
                {"Snacks", "Samosa, veg puffs, cutlets, tea, cookies", 600.0,
                        "https://images.unsplash.com/photo-1626201850129-a96d057d0e67?auto=format&fit=crop&w=900&q=80"},
                {"Veg Thali", "2 sabzi, dal, rice, roti, raita, sweet", 900.0,
                        "https://images.unsplash.com/photo-1574653853027-48cb61a26f2a?auto=format&fit=crop&w=900&q=80"},
                {"Non-Veg Thali", "Chicken curry, egg masala, rice, roti, salad", 1100.0,
                        "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=900&q=80"}
        };
        for (Object[] row : rows) {
            Menu m = new Menu();
            m.setServiceId(serviceId);
            m.setMenuName((String) row[0]);
            m.setItems((String) row[1]);
            m.setPrice((Double) row[2]);
            m.setImageUrl((String) row[3]);
            menuRepository.save(m);
        }
    }
}
