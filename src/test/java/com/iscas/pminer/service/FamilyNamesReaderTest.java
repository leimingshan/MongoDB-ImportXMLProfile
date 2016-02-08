package com.iscas.pminer.service;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for Class FamilyNamesReader
 * @author Mingshan Lei
 * @since 0.9
 * Created by LMSH on 2016/1/27.
 */
public class FamilyNamesReaderTest {

    @Test
    public void testGetInstance() throws Exception {
        assertTrue(FamilyNamesReader.getInstance() != null);
    }

    @Test
    public void testCheckName() throws Exception {
        FamilyNamesReader reader = FamilyNamesReader.getInstance();
        assertTrue(reader.checkName("李阳"));
        assertTrue(reader.checkName("赵匡胤"));

        assertFalse(reader.checkName(""));
        assertFalse(reader.checkName("太长的姓名"));
        assertFalse(reader.checkName("的好"));
        assertFalse(reader.checkName("李"));
        assertFalse(reader.checkName("李太白太白"));
    }
}
