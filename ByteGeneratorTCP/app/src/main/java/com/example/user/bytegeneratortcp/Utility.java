package com.example.user.bytegeneratortcp;

import java.util.Random;

/**
 * Created by User on 1/28/2020.
 */

public class Utility {
    public static Random random = new Random();
    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String bytesToHex(byte[] bytes, int offset, int len) {
        char[] hexChars = new char[len * 2];
        for (int j = 0; j < len; j++) {
            int v = bytes[j+offset] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static int getRandomData(byte [] array,int offset,int len) {
        for(int i=0;i<len;i++){
            array[offset+i] = (byte) random.nextInt(256);
            if(array[offset+i]<0) array[offset+i]*=-1;
        }
        return offset+len;
    }
}