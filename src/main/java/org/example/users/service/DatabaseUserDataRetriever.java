package org.example.users.service;

import org.example.users.model.UserData;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RequestScoped
@Database
class DatabaseUserDataRetriever implements UserDataRetriever {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserData> findUsersByFirstAndLastName(String firstName, String lastName) {
        TypedQuery<UserData> query = entityManager.createQuery("SELECT userData FROM UserData userData WHERE userData.firstName = :firstName AND userData.lastName = :lastName", UserData.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);

        return query.getResultList();
    }

    @Override
    public List<UserData> findUsersByFirstName(String firstName) {
        TypedQuery<UserData> query = entityManager.createQuery("SELECT userData FROM UserData userData WHERE userData.firstName = :firstName", UserData.class);
        query.setParameter("firstName", firstName);

        return query.getResultList();
    }
}
