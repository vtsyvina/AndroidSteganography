package edu.gsu.steganography.service;

import android.graphics.Bitmap;

import com.ayush.imagesteganographylibrary.Text.ImageSteganography;

public interface ImageService {

    ImageSteganography encodeImage(String message, String secretKey, Bitmap image);


    ImageSteganography decodeImage(String secretKey, Bitmap image);



}
