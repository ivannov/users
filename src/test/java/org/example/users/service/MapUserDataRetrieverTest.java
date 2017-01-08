package org.example.users.service;

import org.junit.Before;

public class MapUserDataRetrieverTest extends BaseUserDataRetrieverTest {

    private MapUserDataRetriever retriever;

    @Before
    public void setup() {
        retriever = new MapUserDataRetriever();
    }

    @Override
    protected UserDataRetriever getUserDataRetriever() {
        return retriever;
    }
}
