package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityManager {
    private static SecurityManager uniqueInstance;

    private SecurityManager(){

    }

    public static SecurityManager getInstance(){
        if(uniqueInstance == null){
            return uniqueInstance = new SecurityManager();
        }
        return uniqueInstance;
    }

    public String getHashedPassword(String password){
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashText = no.toString(16);

            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }

            return hashText;
        }

        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e);
            String hashText = "";
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        }
    }
}
