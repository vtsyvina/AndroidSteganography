package edu.gsu.steganography.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import java.util.logging.Logger;

import edu.gsu.steganography.model.SecretKey;
import edu.gsu.steganography.service.SecretKeyService;
import edu.gsu.steganography.service.ServiceFactory;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    private final SecretKeyService keyService = ServiceFactory.getSecretKeyService();

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // Programmatically initialize the scanner view
        mScannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        String[] permissos = {"android.permission.CAMERA"};

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA}, 0);
        }
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Prints scan results
        Log.d("result", rawResult.getText());
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.d("result", rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent(this, SercetKeyListActivity.class);
        //intent.putExtra("secret_key", rawResult.getText());
        String[] split = rawResult.getText().split(":");
        if(split.length != 2){
            Toast.makeText(this, "QR does not contain the secret key", Toast.LENGTH_SHORT).show();
            mScannerView.resumeCameraPreview(this);
            return;
        }
        SecretKey key = new SecretKey(null, split[0], split[1]);
        keyService.insertKey(key);
        startActivity(intent);
        finish();
    }
}
