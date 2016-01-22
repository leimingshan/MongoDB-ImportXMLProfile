package com.iscas.pminer.xml2mongo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iscas.pminer.service.MongoDBRiver;

/**
 * xml2mongo main App:
 * import XML files to MongoDB
 * 该程序用于导入xml数据文件到MongoDB数据库
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("PMiner Xml to Mongo App Start！");

        MongoDBRiver river = new MongoDBRiver();

        String dataRootPath = null;

        String personFolder = null;

        if (args.length == 0) {
            // 读取默认配置文件中的路径到dataRootPath
            try {
                InputStream input = new FileInputStream("C:\\pminer.properties");
                Properties rootProps = new Properties();
                rootProps.load(input);
                dataRootPath = rootProps.getProperty("PminerData");
                logger.info("Import data root path: {}", dataRootPath);
                river.importDataFolder(dataRootPath);
            } catch (IOException e) {
                logger.info(e.getMessage());
                e.printStackTrace();
            }
        } else if (args.length == 2) {
            if (args[0].equals("-d")) { // import data folder
                dataRootPath = args[1];
                logger.info("Import data root path: {}", dataRootPath);
                river.importDataFolder(dataRootPath);
                return;
            } else if (args[0].equals("-p")) { // import personal folder
                personFolder = args[1];
                logger.info("Import person folder path: {}", personFolder);
                river.importPersonFolder(personFolder);
                return;
            }
        } else {
            System.out.println("Xml2Mongo App 命令行参数错误!");
            System.out.println("用法：java -jar xml2mongo.jar -d [root data folder]");
            System.out.println("  或：java -jar xml2mongo.jar -p [person folder]");
        }
    }
}
