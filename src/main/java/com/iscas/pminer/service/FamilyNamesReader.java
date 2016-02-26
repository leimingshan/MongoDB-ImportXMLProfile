package com.iscas.pminer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Read chinese family names from text file.
 * Store them in set for further judgement.
 * @author Mingshan Lei
 * @since 0.1
 */
public class FamilyNamesReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyNamesReader.class);

    private static final int BYTES_PER_CN_CHAR_IN_UTF8 = 3;

    private static final int MIN_NAME_LENGTH = 2;

    private static final int MAX_NAME_LENGTH = 5;

    private Set<String> familyNameSet = new HashSet<>();

    private static FamilyNamesReader instance = new FamilyNamesReader();

    /**
     * Private constructor.
     */
    private FamilyNamesReader() {
        // Initialize reading family name set from text file.
        InputStream input =
            FamilyNamesReader.class.getClassLoader().getResourceAsStream("family-names.txt");
        LOGGER.info("Reading family-names.txt......");

        String lineString;
        try {
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            while ((lineString = bufferReader.readLine()) != null) {
                for (String name : lineString.split(" ")) {
                    familyNameSet.add(name);
                }
            }
            bufferReader.close();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("FamilyNamesReader constructor exception.", e);
        } catch (IOException e) {
            LOGGER.info("FamilyNamesReader constructor exception.", e);
        }
        LOGGER.info("Reading family-names.txt finished.");
    }

    public static FamilyNamesReader getInstance() {
        return instance;
    }

    /**
     * Check whether one string of name satisfy common style.
     * @param name
     * @return whether the name string passed common name style check.
     */
    public boolean checkName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        String familyName = name.substring(0, 1);

        int byteSize = 0;
        try {
            byteSize = name.getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("Check name exception.", e);
            return false;
        }

        if (familyNameSet.contains(familyName)) {
            // check the name length
            // in utf-8 encoding one zh_CN character takes 3 bytes: utf-8编码中一个中文字符占3个字节
            return !(byteSize > MAX_NAME_LENGTH * BYTES_PER_CN_CHAR_IN_UTF8
                || byteSize < MIN_NAME_LENGTH * BYTES_PER_CN_CHAR_IN_UTF8);
        }
        return false;
    }
}
