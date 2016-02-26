package com.iscas.pminer.service;

import com.iscas.pminer.entity.Profile;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Test for PersonFolderParser
 * @author Mingshan Lei
 * @since 0.9
 * Created by LMSH on 2016/2/9.
 */
public class PersonFolderParserTest {

    @Test
    public void testGetProfile() throws Exception {
        PersonFolderParser parser = new PersonFolderParser(new File(this.getClass().getClassLoader()
            .getResource("data/1-PersonFile_1/00b738c9-d02a-4a15-ad0e-9927f3dcca71")
            .getFile()));
        Profile profile = parser.getProfile();
        assertTrue(profile != null);
        assertTrue(profile.getGender().equals("男"));
        assertTrue(profile.getNation().equals("汉族"));
        assertTrue(profile.getImage().length > 0);

        parser = new PersonFolderParser(new File(this.getClass().getClassLoader()
            .getResource("data/1-PersonFile_1/00b738c9-d02a-4a15-ad0e-9927f3dcca71/info.txt")
            .getFile()));
        profile = parser.getProfile();
        assertNull(profile);
    }

}
