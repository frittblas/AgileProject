package se.hkr.agileproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

    // Returns a string with 61 chars
    public String generateSecurePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();

            for (byte b : digest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
    }

    // Encrypt users input to see if it matches password saved in db
    public boolean verifyUserPassword(String providedPassword, String storedPassword) {
        boolean confirmed = false;
        String newSecurePassword = generateSecurePassword(providedPassword);
        if (newSecurePassword.equals(storedPassword))
            confirmed = true;

        return confirmed;
    }
}
