package edu.gsu.steganography.Utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.ayush.imagesteganographylibrary.Text.ImageSteganography;

import java.lang.reflect.Field;

import edu.gsu.steganography.model.ImageSteganographyV2;

public class Utils {

    //Tag for Log
    private static String TAG = Utils.class.getName();

    public static ImageSteganography buildImageSteganography(String  message, String secretKey, Bitmap original_image){
        ImageSteganography imageSteganography = new ImageSteganographyV2(message,
                secretKey,
                original_image);
        //Utils.setImageSteganographySecretKey(imageSteganography, secretKey);
        return imageSteganography;
    }

    public static ImageSteganography buildImageSteganography(String secretKey, Bitmap original_image){
        ImageSteganography imageSteganography = new ImageSteganographyV2(
                secretKey,
                original_image);
        //Utils.setImageSteganographySecretKey(imageSteganography, secretKey);
        return imageSteganography;
    }


    private static void setImageSteganographySecretKey(ImageSteganography imageSteganography, String secretKey){
        Field declaredField =  null;
        try {

            declaredField = ImageSteganography.class.getDeclaredField("secret_key");
            boolean accessible = declaredField.isAccessible();
            declaredField.setAccessible(true);
            declaredField.set(imageSteganography, convertKeyTo128bit(secretKey));
            declaredField.setAccessible(accessible);
        } catch (NoSuchFieldException
                | SecurityException
                | IllegalArgumentException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * It fixes the bug inside the library
     * @param secret_key Secret key to set convert
     * @return secret key of length 16
     */
    private static String convertKeyTo128bit(String secret_key){

        StringBuilder result = new StringBuilder(secret_key);

        if (secret_key.length() <= 16){
            for (int i = 0; i < (16 - secret_key.length()); i++){
                result.append("#");
            }
        }
        else {
            result = new StringBuilder(result.substring(0, 16));
        }

        Log.d(TAG, "Secret Key Length : " + result.toString().getBytes().length);

        return result.toString();
    }
}
