package org.example.users.service;

import org.example.users.model.UserData;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@RunWith(Arquillian.class)
public class DatabaseUserRetrieverServiceTest extends BaseUserDataRetrieverTest {

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class)
                                .addClasses(UserData.class, UserDataRetriever.class,
                                            DatabaseUserDataRetriever.class, Database.class,
                                            BaseUserDataRetrieverTest.class)
                                .addAsResource("META-INF/persistence.xml");
        System.out.println(archive.toString(true));
        return archive;
    }

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction tx;

    @Inject @Database
    private UserDataRetriever retriever;

    @Before
    public void setUp() throws Exception {
        tx.begin();
        em.joinTransaction();
        em.createQuery("delete from UserData ").executeUpdate();
        tx.commit();

        tx.begin();
        em.joinTransaction();
        em.persist(new UserData("Jon", "Snow", "Mr."));
        em.persist(new UserData("Jon", "Doe", "Dr."));
        em.persist(new UserData("Shaleen", "Mishra", "Mr."));
        tx.commit();
    }

    @Override
    protected UserDataRetriever getUserDataRetriever() {
        return retriever;
    }
}
