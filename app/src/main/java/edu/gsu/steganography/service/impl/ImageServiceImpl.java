package edu.gsu.steganography.service.impl;

import android.graphics.Bitmap;

import com.ayush.imagesteganographylibrary.Text.ImageSteganography;

import edu.gsu.steganography.service.ImageService;

public class ImageServiceImpl implements ImageService {


    @Override
    public ImageSteganography decodeImage(String secretKey, Bitmap image) {
        return new ImageSteganography(secretKey,
                image);
    }


    @Override
    public ImageSteganography encodeImage(String message, String secretKey, Bitmap image) {
        return new ImageSteganography(message,
                secretKey,
                image);
    }
}
