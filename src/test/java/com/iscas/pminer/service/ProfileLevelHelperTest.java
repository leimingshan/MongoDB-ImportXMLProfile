package com.iscas.pminer.service;

import com.iscas.pminer.entity.Profile;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test for ProfileLevelHelper
 * @author Mingshan Lei
 * @since 2016/2/5
 */
public class ProfileLevelHelperTest {

    private ProfileLevelHelper helper = new ProfileLevelHelper();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetLevel() throws Exception {
        Profile testProfile1 = mock(Profile.class);
        assertEquals(helper.getLevel(testProfile1), 3);

        when(testProfile1.getName()).thenReturn("");
        assertEquals(helper.getLevel(testProfile1), 3);

        Profile testProfile2 = mock(Profile.class);
        when(testProfile2.getName()).thenReturn("张三");
        when(testProfile2.getGender()).thenReturn("男");
        assertEquals(helper.getLevel(testProfile2), 3);

        Profile testProfile3 = mock(Profile.class);
        when(testProfile3.getName()).thenReturn("张三");
        when(testProfile3.getGender()).thenReturn("男");
        when(testProfile3.getNation()).thenReturn("汉");
        when(testProfile3.getAge()).thenReturn("30");
        when(testProfile3.getBirthProvince()).thenReturn("河南省");
        assertEquals(helper.getLevel(testProfile3), 2);
    }

}
