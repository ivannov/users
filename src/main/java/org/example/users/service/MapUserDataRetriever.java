package org.example.users.service;

import org.example.users.model.UserData;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
@MemoryMap
class MapUserDataRetriever implements UserDataRetriever {

    private Map<String, List<UserData>> usersMap;

    MapUserDataRetriever() {
        usersMap = new HashMap<>();
        usersMap.put("Jon", Arrays.asList(
                new UserData("Jon", "Snow", "Mr."),
                new UserData("Jon", "Doe", "Dr.")));
        usersMap.put("Shaleen", Collections.singletonList(
                new UserData("Shaleen", "Mishra", "Mr.")
        ));
    }

    @Override
    public List<UserData> findUsersByFirstAndLastName(String firstName, String lastName) {
        List<UserData> allUsersWithFirstName = getAllUsersForFirstName(firstName);
        UserData searched = new UserData(firstName, lastName);
        filterUserData(allUsersWithFirstName, searched);
        return allUsersWithFirstName;
    }

    @Override
    public List<UserData> findUsersByFirstName(String firstName) {
        return getAllUsersForFirstName(firstName);
    }

    private List<UserData> getAllUsersForFirstName(String firstName) {
        List<UserData> users = usersMap.get(firstName);
        return users == null ? new ArrayList<UserData>() : new ArrayList<>(users);
    }

    private void filterUserData(List<UserData> allUsersWithFirstName, UserData searched) {
        Iterator<UserData> userDataIterator = allUsersWithFirstName.iterator();
        while (userDataIterator.hasNext()) {
            UserData user = userDataIterator.next();
            if (!user.equals(searched)) {
                userDataIterator.remove();
            }
        }
    }

}
