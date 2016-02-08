package com.iscas.pminer.service;

import com.iscas.pminer.entity.OfficeRecord;
import com.iscas.pminer.entity.Profile;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test for ProfileLevelHelper
 * @author Mingshan Lei
 * @since 0.9
 * Created by LMSH on 2016/2/5.
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
        when(testProfile1.getName()).thenReturn("-");
        assertEquals(helper.getLevel(testProfile1), 3);
        when(testProfile1.getName()).thenReturn("--");
        assertEquals(helper.getLevel(testProfile1), 3);
        when(testProfile1.getName()).thenReturn("---");
        assertEquals(helper.getLevel(testProfile1), 3);

        Profile testProfile2 = mock(Profile.class);
        when(testProfile2.getName()).thenReturn("张三");
        when(testProfile2.getGender()).thenReturn("男");
        assertEquals(helper.getLevel(testProfile2), 3);
        when(testProfile2.getGender()).thenReturn("");
        assertEquals(helper.getLevel(testProfile2), 3);

        when(testProfile2.getGender()).thenReturn("男");
        when(testProfile2.getNation()).thenReturn("汉");
        assertEquals(helper.getLevel(testProfile2), 3);
        when(testProfile2.getAge()).thenReturn("30");
        when(testProfile2.getNation()).thenReturn("-");
        assertEquals(helper.getLevel(testProfile2), 3);

        Profile testProfile3 = mock(Profile.class);
        when(testProfile3.getName()).thenReturn("张三");
        when(testProfile3.getGender()).thenReturn("男");
        when(testProfile3.getNation()).thenReturn("汉");
        when(testProfile3.getAge()).thenReturn("30");
        when(testProfile3.getBirthProvince()).thenReturn("河南省");
        assertEquals(helper.getLevel(testProfile3), 2);

        when(testProfile3.getBirthProvince()).thenReturn(null);
        assertEquals(helper.getLevel(testProfile3), 3);

        Profile testProfile4 = mock(Profile.class);
        when(testProfile4.getName()).thenReturn("李四");
        when(testProfile4.getGender()).thenReturn("男");
        when(testProfile4.getNation()).thenReturn("汉");
        when(testProfile4.getAge()).thenReturn("30");
        when(testProfile4.getBirthProvince()).thenReturn("河南省");
        when(testProfile4.getBirthDate()).thenReturn("---");
        when(testProfile4.getWorkDate()).thenReturn("19900101");
        assertEquals(helper.getLevel(testProfile4), 2);
        when(testProfile4.getBirthDate()).thenReturn("19500101");
        when(testProfile4.getWorkDate()).thenReturn("---");
        assertEquals(helper.getLevel(testProfile4), 2);

        when(testProfile4.getBirthDate()).thenReturn("19500101");
        when(testProfile4.getWorkDate()).thenReturn("---");
        assertEquals(helper.getLevel(testProfile4), 2);

        when(testProfile4.getBirthDate()).thenReturn("19500101");
        when(testProfile4.getWorkDate()).thenReturn("19790101");
        when(testProfile4.getOfficeRecord()).thenReturn(null);
        assertEquals(helper.getLevel(testProfile4), 2);
        List<OfficeRecord> list = mock(List.class);
        when(list.size()).thenReturn(3);
        when(testProfile4.getOfficeRecord()).thenReturn(list);
        assertEquals(helper.getLevel(testProfile4), 2);
        when(list.size()).thenReturn(8);
        System.out.println(testProfile4.getOfficeRecord().size());
        assertEquals(helper.getLevel(testProfile4), 1);
    }

}
