package com.iscas.pminer.xml2mongo;

import org.junit.Test;

/**
 * Test for main.
 *
 * @author Mingshan Lei
 * @since 2016/2/26
 */
public class AppTest {
    @Test
    public void testMain() throws Exception {
        String[] args = {};
        App.main(args);
    }

    @Test
    public void testMain2() throws Exception {
        String[] args2 = {"-d", "test", "test"};
        App.main(args2);
        String[] args3 = {"-d", "test"};
        App.main(args3);
        String[] args4 = {"-p", "test"};
        App.main(args4);
    }
}
