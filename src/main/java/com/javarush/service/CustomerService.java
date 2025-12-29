package com.javarush.service;

import com.javarush.dao.*;
import com.javarush.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CustomerService {

    private final SessionFactory sessionFactory;
    private final CustomerDAO customerDAO;
    private final AddressDAO addressDAO;
    private final CityDAO cityDAO;
    private final StoreDAO storeDAO;

    public CustomerService(SessionFactory sessionFactory,
                           CustomerDAO customerDAO,
                           AddressDAO addressDAO,
                           CityDAO cityDAO,
                           StoreDAO storeDAO) {
        this.sessionFactory = sessionFactory;
        this.customerDAO = customerDAO;
        this.addressDAO = addressDAO;
        this.cityDAO = cityDAO;
        this.storeDAO = storeDAO;
    }

    public Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            Store store = storeDAO.getItems(0, 1).get(0);
            City city = cityDAO.getByName("Kragujevac");

            Address address = new Address();
            address.setAddress("Indeo str, 53");
            address.setPhone("666-333-999");
            address.setCity(city);
            address.setDistrict("Ahahahaha");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setActive(true);
            customer.setEmail("test@gmail.com");
            customer.setFirstName("Vasya");
            customer.setLastName("Pupkin");
            customer.setStore(store);
            customerDAO.save(customer);

            transaction.commit();
            return customer;
        }
    }
}

