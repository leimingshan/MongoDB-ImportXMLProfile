package com.iscas.pminer.service;

import com.iscas.pminer.entity.Profile;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Test class for XmlParser
 * @author Mingshan Lei
 * @since 0.9
 * Created by LMSH on 2016/2/8.
 */
public class XmlParserTest {

    @Test
    public void testGetProfile() throws Exception {
        XmlParser xmlParser = new XmlParser(new File(this.getClass().getClassLoader()
            .getResource("data/1-PersonFile_1/00b738c9-d02a-4a15-ad0e-9927f3dcca71/info.xml")
            .getFile()));
        Profile profile = xmlParser.getProfile();
        assertTrue(profile != null);
        assertTrue(profile.getGender().equals("男"));
        assertTrue(profile.getNation().equals("汉族"));
    }

    @Test
    public void testIsYearLegal() throws Exception {
        assertTrue(XmlParser.isYearLegal("-"));
        assertFalse(XmlParser.isYearLegal("1880"));

        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        assertFalse(XmlParser.isYearLegal(String.valueOf(currentYear + 10)));
        assertTrue(XmlParser.isYearLegal("2010"));
        assertTrue(XmlParser.isYearLegal("1990"));
    }

}
