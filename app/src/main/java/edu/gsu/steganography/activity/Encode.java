package edu.gsu.steganography.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ayush.imagesteganographylibrary.Text.AsyncTaskCallback.TextEncodingCallback;
import com.ayush.imagesteganographylibrary.Text.ImageSteganography;
import com.ayush.imagesteganographylibrary.Text.TextEncoding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.gsu.steganography.R;
import edu.gsu.steganography.utils.Utils;
import edu.gsu.steganography.model.SecretKey;
import edu.gsu.steganography.model.SecretKeyArrayAdapter;
import edu.gsu.steganography.service.SecretKeyService;
import edu.gsu.steganography.service.ServiceFactory;

public class Encode extends AppCompatActivity implements TextEncodingCallback {

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "Encode Class";
    //Created variables for UI
    private TextView whether_encoded;
    private ImageView imageView;
    private EditText message;
    private EditText secret_key;
    //Objects needed for encoding
    private TextEncoding textEncoding;
    private ImageSteganography imageSteganography;
    private ProgressDialog save;
    private Uri filepath;
    //Bitmaps
    private Bitmap original_image;
    private Bitmap encoded_image;

    private boolean imageEncoded;

    private final SecretKeyService keyService = ServiceFactory.getSecretKeyService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        //initialized the UI components

        whether_encoded = findViewById(R.id.whether_encoded);

        imageView = findViewById(R.id.imageview);

        message = findViewById(R.id.message);
        secret_key = findViewById(R.id.secret_key_edit);

        Button choose_image_button = findViewById(R.id.choose_image_button);
        Button encode_button = findViewById(R.id.encode_button);
        Button save_image_button = findViewById(R.id.save_image_button);
        Button select_key = findViewById(R.id.choose_secret_key_button);
        ListView keysListView = findViewById(R.id.key_list_view);

        checkAndRequestPermissions();


        //Choose image button
        choose_image_button.setOnClickListener(view -> {
            imageEncoded = false;
            ImageChooser();
        });

        //Encode Button
        encode_button.setOnClickListener(view -> {
            whether_encoded.setText("");
            if (filepath != null) {
                if (message.getText() != null) {

                    //ImageSteganography Object instantiation
                    imageSteganography = Utils.buildImageSteganography(message.getText().toString(), secret_key.getText().toString(), original_image);
                    //TextEncoding object Instantiation
                    textEncoding = new TextEncoding(Encode.this, Encode.this);
                    //Executing the encoding
                    textEncoding.execute(imageSteganography);
                }
            } else {
                Toast.makeText(this, "Choose image first", Toast.LENGTH_SHORT).show();
            }
        });

        //Save image button
        save_image_button.setOnClickListener(view -> {
            final Bitmap imgToSave = encoded_image;
            if (imgToSave == null) {
                Toast.makeText(this, "Choose image first", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!imageEncoded) {
                Toast.makeText(this, "Message is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            Thread PerformEncoding = new Thread(() -> saveToInternalStorage(imgToSave));
            save = new ProgressDialog(Encode.this);
            save.setMessage("Saving, Please Wait...");
            save.setTitle("Saving Image");
            save.setIndeterminate(false);
            save.setCancelable(false);
            save.show();
            PerformEncoding.start();
        });

        //Select key button
        select_key.setOnClickListener(view -> {
            ArrayAdapter<SecretKey> adapter = new SecretKeyArrayAdapter(this, keyService.allKeys());
            keysListView.setAdapter(adapter);
            keysListView.setVisibility(View.VISIBLE);
        });

        // Select key view
        keysListView.setOnItemClickListener((adapter, view, position, id) -> {
            SecretKey key = (SecretKey) adapter.getItemAtPosition(position);
            secret_key.setText(key.getKey(), TextView.BufferType.EDITABLE);
            keysListView.setVisibility(View.GONE);
        });

    }

    private void ImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Image set to imageView
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filepath = data.getData();
            try {
                original_image = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);

                imageView.setImageBitmap(original_image);
            } catch (IOException e) {
                Log.d(TAG, "Error : " + e);
            }
        }

    }

    // Override method of TextEncodingCallback

    @Override
    public void onStartTextEncoding() {
        //Whatever you want to do at the start of text encoding
    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography result) {

        //By the end of textEncoding

        if (result != null && result.isEncoded()) {
            encoded_image = result.getEncoded_image();
            whether_encoded.setText("Encoded");
            imageView.setImageBitmap(encoded_image);
            imageEncoded = true;
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage) {
        OutputStream fOut;
        EditText filenameEdit = findViewById(R.id.filename_edit);
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), filenameEdit.getText().toString()+ ".png"); // the File to save ,
        try {
            fOut = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
            whether_encoded.post(new Runnable() {
                @Override
                public void run() {
                    save.dismiss();
                }
            });
        } catch (IOException e) {
            //Toast.makeText(this, "There was an error while saving the image", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void checkAndRequestPermissions() {
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ReadPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), 1);
        }
    }


}
