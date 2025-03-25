package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.entity.Place;
import ru.minusd.security.repository.PlaceRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepository {
    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Optional<Place> save(Place entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Place> findById(Long id) {
        try (Session session = getSession()) {
            return Optional.ofNullable(session.get(Place.class,id));
        }

    }

    @Override
    public Optional<List<Place>> findAll() {
        try (Session session = getSession()) {
            List<Place> places = session.createQuery("from Place").list();
            return Optional.ofNullable(places);
        }

    }

    @Override
    public void deleteById(Long primaryKey) {

    }
}
