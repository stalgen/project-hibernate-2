package com.javarush.service;

import com.javarush.dao.*;
import com.javarush.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentalService {

    private final SessionFactory sessionFactory;
    private final RentalDAO rentalDAO;
    private final InventoryDAO inventoryDAO;
    private final FilmDAO filmDAO;
    private final PaymentDAO paymentDAO;

    public RentalService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.rentalDAO = new RentalDAO(sessionFactory);
        this.inventoryDAO = new InventoryDAO(sessionFactory);
        this.filmDAO = new FilmDAO(sessionFactory);
        this.paymentDAO = new PaymentDAO(sessionFactory);
    }

    public void returnInventory() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    public void rentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = filmDAO.getFirstAvailableFilmForRent();
            Store store = customer.getStore();
            Staff staff = store.getStaff();

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setRentalDate(LocalDateTime.now());
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setRental(rental);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setAmount(BigDecimal.valueOf(55.77));
            payment.setStaff(staff);
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }
}

