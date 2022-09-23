package edu.byu.cs.tweeter.server.util;

import java.util.Random;
public class RandomIDGenerator {
    public RandomIDGenerator() {
    }

    public static String createId(int stringLength){
        Random rand = new Random();
        StringBuilder newId = new StringBuilder();
        for(int i=0; i <stringLength;i++){
            int letterOrNumber = rand.nextInt(3);
            if(letterOrNumber ==0){
                newId.append(Character.toChars(rand.nextInt(10)+'0'));
            }
            else if (letterOrNumber ==1){
                newId.append(Character.toChars(rand.nextInt(26)+'A'));
            }
            else {
                newId.append(Character.toChars(rand.nextInt(26)+'a'));
            }
        }
        return newId.toString();
    }
}