package me.aravinth.passphrase;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by aravinth on 17-Apr-15.
 */
public class Random {
    private static SecureRandom random = new SecureRandom();

    public static String getPassword(int l) {
        return new BigInteger(130, random).toString(32).substring(0,l);
    }
}
