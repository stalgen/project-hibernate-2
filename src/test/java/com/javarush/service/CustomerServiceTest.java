package com.javarush.service;

import com.javarush.dao.*;
import com.javarush.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;

    @Mock
    private CustomerDAO customerDAO;
    @Mock
    private AddressDAO addressDAO;
    @Mock
    private CityDAO cityDAO;
    @Mock
    private StoreDAO storeDAO;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldCreateCustomer() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        when(storeDAO.getItems(0, 1)).thenReturn(List.of(new Store()));
        when(cityDAO.getByName("Kragujevac")).thenReturn(new City());

        Customer customer = customerService.createCustomer();

        assertNotNull(customer);
        verify(addressDAO).save(any(Address.class));
        verify(customerDAO).save(any(Customer.class));
        verify(transaction).commit();
    }
}


