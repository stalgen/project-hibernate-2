package com.javarush;

import com.javarush.config.HibernateUtil;
import com.javarush.dao.*;
import com.javarush.domain.Customer;
import com.javarush.service.*;
import org.hibernate.SessionFactory;

public class ApplicationRunner {

    public void run() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        CustomerService customerService = new CustomerService(
                sessionFactory,
                new CustomerDAO(sessionFactory),
                new AddressDAO(sessionFactory),
                new CityDAO(sessionFactory),
                new StoreDAO(sessionFactory)
        );

        RentalService rentalService = new RentalService(sessionFactory);

        FilmService filmService = new FilmService(sessionFactory);

        Customer customer = customerService.createCustomer();
        rentalService.returnInventory();
        rentalService.rentInventory(customer);
        filmService.createNewFilm();
    }
}

