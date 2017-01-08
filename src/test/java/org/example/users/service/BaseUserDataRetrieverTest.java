package org.example.users.service;

import org.example.users.model.UserData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class BaseUserDataRetrieverTest {

    protected abstract UserDataRetriever getUserDataRetriever();

    @Test
    public void shouldFindUserWithMatchingFirstAndLastName() throws Exception {
        List<UserData> users = getUserDataRetriever().findUsersByFirstAndLastName("Jon", "Snow");
        assertEquals(1, users.size());
        UserData user = users.get(0);
        assertEquals("Jon", user.getFirstName());
        assertEquals("Snow", user.getLastName());
    }

    @Test
    public void shouldFindUsersWhoMatchSameFirstName() throws Exception {
        List<UserData> users = getUserDataRetriever().findUsersByFirstName("Jon");
        assertEquals(2, users.size());
        assertTrue(users.contains(new UserData("Jon", "Snow")));
        assertTrue(users.contains(new UserData("Jon", "Doe")));
    }

    @Test
    public void shouldReturnEmptyListWhenJustOneNameMatches() throws Exception {
        assertEquals(0, getUserDataRetriever().findUsersByFirstAndLastName("Jon", "Johnson").size());
        assertEquals(0, getUserDataRetriever().findUsersByFirstAndLastName("John", "Snow").size());
    }

    @Test
    public void shouldReturnEmptyListWhenNoNameMatches() throws Exception {
        assertEquals(0, getUserDataRetriever().findUsersByFirstAndLastName("Foo", "Bar").size());
        assertEquals(0, getUserDataRetriever().findUsersByFirstName("Foo").size());
    }

}
