package ca.cmpt276.examharmony.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

//takes in uuid, returns hashed string
public class HashUtils {

    public String SHA256(UUID prtUUID) throws NoSuchAlgorithmException {
        String uuidString = prtUUID.toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(uuidString.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
