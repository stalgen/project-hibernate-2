package com.javarush.service;

import com.javarush.dao.*;
import com.javarush.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilmService {

    private final SessionFactory sessionFactory;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final LanguageDAO languageDAO;
    private final CategoryDAO categoryDAO;
    private final ActorDAO actorDAO;

    public FilmService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.filmDAO = new FilmDAO(sessionFactory);
        this.filmTextDAO = new FilmTextDAO(sessionFactory);
        this.languageDAO = new LanguageDAO(sessionFactory);
        this.categoryDAO = new CategoryDAO(sessionFactory);
        this.actorDAO = new ActorDAO(sessionFactory);
    }

    public void createNewFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Language language = languageDAO.getItems(0, 20).get(0);
            List<Category> categories = categoryDAO.getItems(0, 5);
            List<Actor> actors = actorDAO.getItems(0, 20);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setRating(Rating.NC17);
            film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
            film.setLength((short) 123);
            film.setReplacementCost(BigDecimal.TEN);
            film.setRentalRate(BigDecimal.ZERO);
            film.setLanguage(language);
            film.setDescription("new film");
            film.setTitle("scary movie");
            film.setRentalDuration((byte) 57);
            film.setOriginalLanguage(language);
            film.setCategories(new HashSet<>(categories));
            film.setYear(Year.now());
            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setId(film.getId());
            filmText.setTitle("scary movie");
            filmText.setDescription("new film text");
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }
}

