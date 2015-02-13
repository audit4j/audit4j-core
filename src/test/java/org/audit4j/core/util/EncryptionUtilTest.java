package org.audit4j.core.util;

import java.security.InvalidKeyException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>EncryptionUtilTest</code> contains tests for the class <code>{@link EncryptionUtil}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:27 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class EncryptionUtilTest {
    /**
     * Run the String decrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.security.InvalidKeyException.class)
    public void testDecrypt_1()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String encrypted = "";

        String result = fixture.decrypt(encrypted);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String decrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.security.InvalidKeyException.class)
    public void testDecrypt_2()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String encrypted = "";

        String result = fixture.decrypt(encrypted);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String decrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.security.InvalidKeyException.class)
    public void testDecrypt_3()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String encrypted = "";

        String result = fixture.decrypt(encrypted);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String decrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.security.InvalidKeyException.class)
    public void testDecrypt_4()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String encrypted = "";

        String result = fixture.decrypt(encrypted);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String encrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testEncrypt_1()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String raw = "";

        String result = fixture.encrypt(raw);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String encrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.security.InvalidKeyException.class)
    public void testEncrypt_2()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String raw = "";

        String result = fixture.encrypt(raw);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String encrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.security.InvalidKeyException.class)
    public void testEncrypt_3()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String raw = "";

        String result = fixture.encrypt(raw);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String encrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.security.InvalidKeyException.class)
    public void testEncrypt_4()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String raw = "";

        String result = fixture.encrypt(raw);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String encrypt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test(expected = java.security.InvalidKeyException.class)
    public void testEncrypt_5()
        throws Exception {
        EncryptionUtil fixture = EncryptionUtil.getInstance("", "");
        String raw = "";

        String result = fixture.encrypt(raw);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the EncryptionUtil getInstance(String,String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetInstance_1()
        throws Exception {
        String key = "";
        String salt = "";

        EncryptionUtil result = EncryptionUtil.getInstance(key, salt);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the EncryptionUtil getInstance(String,String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetInstance_2()
        throws Exception {
        String key = "";
        String salt = "";

        EncryptionUtil result = EncryptionUtil.getInstance(key, salt);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the EncryptionUtil getInstance(String,String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetInstance_3()
        throws Exception {
        String key = "";
        String salt = "";

        EncryptionUtil result = EncryptionUtil.getInstance(key, salt);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the EncryptionUtil getInstance(String,String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetInstance_4()
        throws Exception {
        String key = "";
        String salt = "";

        EncryptionUtil result = EncryptionUtil.getInstance(key, salt);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the EncryptionUtil getInstance(String,String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetInstance_5()
        throws Exception {
        String key = "";
        String salt = "";

        EncryptionUtil result = EncryptionUtil.getInstance(key, salt);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Before
    public void setUp()
        throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *         if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(EncryptionUtilTest.class);
    }
}