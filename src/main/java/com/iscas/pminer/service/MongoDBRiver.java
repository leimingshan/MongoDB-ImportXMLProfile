package com.iscas.pminer.service;

import com.iscas.pminer.entity.Profile;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.File;
import java.net.UnknownHostException;

public class MongoDBRiver {
    private MongoClient mongoClient;
    private Datastore dataStore;

    public MongoDBRiver() throws UnknownHostException {
        mongoClient = new MongoClient("localhost");
        Morphia morphia = new Morphia();
        dataStore = morphia.createDatastore(mongoClient, "pminer");

        morphia.map(Profile.class);

        dataStore.ensureIndexes(); // 加入针对_id和姓名的索引
        // 加入其他用于查询关系和统计等信息的索引
        dataStore.ensureIndex(Profile.class, "officeRecord.tupleList.unitNameList");
        dataStore.ensureIndex(Profile.class, "latestOfficeRecord.province");
        dataStore.ensureIndex(Profile.class, "latestOfficeRecord.rank");
        dataStore.ensureIndex(Profile.class, "studyRecord.universityName");
        dataStore.ensureIndex(Profile.class, "birthProvince");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
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
        for (int j = 0; j < dirList.length; j++) {
            if (!dirList[j].isDirectory()) {     // dirList[j] should be PersonFile_1 or 2, etc.
                System.out.println("Error: " + dirList[j].getName() + " is not a category folder!");
                continue;
            }
            File[] personFolders = dirList[j].listFiles();//person folders

            for (int i = 0; i < personFolders.length; i++) {

                File personFolder = personFolders[i];

                System.out.println("Scan folder: " + personFolder.getName());

                Profile profile = new PersonFolderParser(personFolder).getProfile();
                if (profile == null) {
                    System.err.println("Error: Cound not parse " + personFolder.getName());
                } else {
                    // 保存当前实体对象到数据库
                    dataStore.save(profile);
                }
            }
        }
        System.out.println("Scan Completed!");
    }

    public void importPersonFolder(String personFolderPath) {
        // scan person folders
        File personFolder = new File(personFolderPath);
        System.out.println("Scan folder: " + personFolder.getName());

        Profile profile = new PersonFolderParser(personFolder).getProfile();
        if (profile == null) {
            System.err.println("Error: Cound not parse " + personFolder.getName());
        } else {
            // 保存当前实体对象到数据库
            dataStore.save(profile);
        }
        System.out.println("Scan Completed!");
    }
}
