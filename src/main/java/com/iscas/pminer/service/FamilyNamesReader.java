package com.iscas.pminer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FamilyNamesReader {

    private static final Logger logger = LoggerFactory.getLogger(FamilyNamesReader.class);

    private Set<String> familyNameSet = new HashSet<String>();

    private static FamilyNamesReader instance = new FamilyNamesReader();

    public static FamilyNamesReader getInstance() {
        return instance;
    }

    private FamilyNamesReader() {
        //初始化读取百家姓文件
        InputStream input =
            FamilyNamesReader.class.getClassLoader().getResourceAsStream("family-names.txt");
        BufferedReader bufferReader = null;
        logger.info("Read family-names.txt......");

        String lineString = null;
        try {
            bufferReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            while ((lineString = bufferReader.readLine()) != null) {
                String[] familyNames = lineString.split(" ");
                for (String name : familyNames) {
                    familyNameSet.add(name);
                }
            }
            bufferReader.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Read family-names.txt finished.");
    }

    public boolean checkName(String name) {
        String familyName = name.substring(0, 1);
        if (familyNameSet.contains(familyName)) {
            //还需要校验中文名字的长度
            //utf-8编码中一个中文字符占3个字节
            try {
                if (name.getBytes("utf-8").length > 15 || name.getBytes("utf-8").length < 6)
                    return false;
                else
                    return true;
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        } else
            return false;
    }
}
