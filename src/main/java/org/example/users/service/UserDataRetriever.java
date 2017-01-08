package org.example.users.service;

import org.example.users.model.UserData;

import java.util.List;

public interface UserDataRetriever {

    List<UserData> findUsersByFirstAndLastName(String firstName, String lastName);

    List<UserData> findUsersByFirstName(String firstName);

}
