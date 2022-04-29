package se.hkr.agileproject;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestEncrypt {
    Encrypt enc = new Encrypt();
    @Test
    public void test_generateSecurePassword(){
        String testString = enc.generateSecurePassword("passwordtest");
        assertEquals("a7574a42198b7d7eee2c3773a0b95558f19545798d6975e681e2055fd5eb9", testString);
    }

    @Test
    public void test_generateSecurePasswordDifferentStrings(){
        String testString1 = enc.generateSecurePassword("passwordtest");
        String testString2 = enc.generateSecurePassword("testpassword");
        assertNotEquals(testString1, testString2);
    }

    @Test
    public void test_generateSecurePasswordLength(){
        String testString = enc.generateSecurePassword("passwordtest");
        assertEquals(testString.length(), 61);
    }

    @Test
    public void test_verifyUserPassword(){
        assertTrue(enc.verifyUserPassword("passwordtest", "a7574a42198b7d7eee2c3773a0b95558f19545798d6975e681e2055fd5eb9"));
    }

}
