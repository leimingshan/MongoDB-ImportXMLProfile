package com.iscas.pminer.service;

import com.iscas.pminer.entity.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for MongoDBRiver class.
 *
 * @author Mingshan Lei
 * @since 0.9
 * Created by LMSH on 2016/2/10.
 */
public class MongoDBRiverTest {

    private MongoDBRiver river;
    private Datastore dataStore;

    @Before
    public void setUp() throws Exception {
        river = new MongoDBRiver();
        dataStore = river.getDataStore();
    }

    @Test
    public void testImportDataFolder() throws Exception {
        river.importDataFolder(this.getClass().getClassLoader().getResource("data").getPath());
        final Query<Profile> query = dataStore.createQuery(Profile.class);
        final List<Profile> profileList = query.asList();
        assertEquals(profileList.size(), 5);
    }

    @Test
    public void testImportPersonFolder() throws Exception {
        river.importPersonFolder(this.getClass().getClassLoader()
            .getResource("data/1-PersonFile_1/0a5cb048-5e5c-431f-9db7-088fe60d51be").getPath());
        final Query<Profile> query = dataStore.createQuery(Profile.class);
        final List<Profile> profileList = query.asList();
        assertEquals(profileList.size(), 1);
    }

    @After
    public void tearDown() throws Exception {
        dataStore.delete(dataStore.createQuery(Profile.class));
    }
}
