package org.example.users.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserDataRetrieverFactory {

    @Inject
    @MemoryMap
    private UserDataRetriever memoryUserDataRetriever;

    @Inject @Database
    private UserDataRetriever databaseUserDataRetriever;

    public UserDataRetriever build() {
        if ("prod".equals(System.getProperty("mode"))) {
            return databaseUserDataRetriever;
        } else {
            return memoryUserDataRetriever;
        }
    }
}
