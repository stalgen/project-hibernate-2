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
        new ApplicationRunner().run();
    }
}
