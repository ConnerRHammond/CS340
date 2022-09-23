package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class DynamoUsersDAO extends DynamoBaseDAO implements UsersDAO {

    public DynamoUsersDAO() {
        super("user");
    }

    @Override
    public User getUser(String alias,String password) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "alias");
        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g",alias);
        ItemCollection<QueryOutcome> items = tableQueryExecuter(nameMap,valueMap,table,"#f = :g",0,null);
        Item item = null;
        Iterator<Item> iterator = null;

        try{
            iterator = items.iterator();
            item = iterator.next();
            User user = new User(item.getString("firstName"), item.getString("lastName"),
                    item.getString("alias"), item.getString("imageUrl"));
            if(password != null && !validatePassword(password,item.getString("password"))){
//            if(password != null && !password.equals(item.getString("password"))){
                return null;
            }
            return user;
        }catch (Exception e){
            System.out.println("Failed");
        }
        return null;
    }

    @Override
    public String createUser(String alias,String firstName,String lastName, String url,String password) {
        try {
            password = generateStorngPasswordHash(password);
            create(new Item().withPrimaryKey("alias", alias)
                    .withString("firstName", firstName).withString("lastName", lastName)
                    .withString("imageUrl", url).withString("password",password), table);
            return "Success";
        }catch (Exception e){
            return e.toString();
        }
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static String generateStorngPasswordHash(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

}


