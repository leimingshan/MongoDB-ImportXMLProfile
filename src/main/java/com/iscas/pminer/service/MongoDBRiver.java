package com.iscas.pminer.service;

import com.iscas.pminer.entity.Profile;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * MongoDBRiver for profile import and save.
 * @author Mingshan Lei
 * @since 0.1
 */
public class MongoDBRiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBRiver.class);

    private Datastore dataStore;

    public MongoDBRiver() {
        final Morphia morphia = new Morphia();

        morphia.map(Profile.class);

        dataStore = morphia.createDatastore(new MongoClient(), "pminer");
        dataStore.ensureIndexes(); // 加入针对_id和姓名的索引

        // 加入其他用于查询关系和统计等信息的索引
        dataStore.ensureIndex(Profile.class, "officeRecord.tupleList.unitNameList");
        dataStore.ensureIndex(Profile.class, "latestOfficeRecord.province");
        dataStore.ensureIndex(Profile.class, "latestOfficeRecord.rank");
        dataStore.ensureIndex(Profile.class, "studyRecord.universityName");
        dataStore.ensureIndex(Profile.class, "birthProvince");
    }

    public Datastore getDataStore() {
        return dataStore;
    }

    public void setDataStore(Datastore dataStore) {
        this.dataStore = dataStore;
    }

    public void importDataFolder(String dataRootPath) {
        // scan data folder which contains many PersonFile and then personFolders
        File dir = new File(dataRootPath);       // data folder
        File[] dirList = dir.listFiles();        // PersonFile_1..n folders
        if (dirList == null || dirList.length == 0) {
            LOGGER.error("Error: this is not profile data folder - " + dataRootPath);
            return;
        }
        for (int j = 0; j < dirList.length; j++) {
            if (!dirList[j].isDirectory()) {     // dirList[j] should be PersonFile_1 or 2, etc.
                LOGGER.error("Error: " + dirList[j].getName() + " is not a category folder!");
                continue;
            }
            File[] personFolders = dirList[j].listFiles();//person folders

            for (int i = 0; i < personFolders.length; i++) {

                File personFolder = personFolders[i];

                LOGGER.info("Scan folder: " + personFolder.getName());

                Profile profile = new PersonFolderParser(personFolder).getProfile();
                if (profile == null) {
                    LOGGER.error("Error: Cound not parse " + personFolder.getName());
                } else {
                    // 保存当前实体对象到数据库
                    dataStore.save(profile);
                }
            }
        }
        LOGGER.info("Scan Completed!");
    }

    public void importPersonFolder(String personFolderPath) {
        // scan person folders
        File personFolder = new File(personFolderPath);
        LOGGER.info("Scan folder: " + personFolder.getName());

        Profile profile = new PersonFolderParser(personFolder).getProfile();
        if (profile == null) {
            LOGGER.error("Error: Could not parse " + personFolder.getName());
        } else {
            // 保存当前实体对象到数据库
            dataStore.save(profile);
        }
        LOGGER.info("Scan Completed!");
    }
}
