package com.javarush;

import com.javarush.config.HibernateUtil;
import com.javarush.dao.AddressDAO;
import com.javarush.dao.CityDAO;
import com.javarush.dao.CustomerDAO;
import com.javarush.dao.StoreDAO;
import com.javarush.service.*;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {
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

        var customer = customerService.createCustomer();
        rentalService.returnInventory();
        rentalService.rentInventory(customer);
        filmService.createNewFilm();
    }
}
