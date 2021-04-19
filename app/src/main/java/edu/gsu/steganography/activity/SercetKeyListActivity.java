package edu.gsu.steganography.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.gsu.steganography.R;
import edu.gsu.steganography.model.SecretKey;
import edu.gsu.steganography.model.SecretKeyArrayAdapter;
import edu.gsu.steganography.service.SecretKeyService;
import edu.gsu.steganography.service.ServiceFactory;

public class SercetKeyListActivity extends AppCompatActivity {

    SecretKeyService keyService = ServiceFactory.getSecretKeyService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_key_list);

        ListView keysListView = findViewById(R.id.key_list_view);
        ArrayAdapter<SecretKey> keyArrayAdapter=new SecretKeyArrayAdapter(this, keyService.allKeys());
        keysListView.setAdapter(keyArrayAdapter);

        // Select key view
        keysListView.setOnItemClickListener((adapter, view, position, id) ->{
            SecretKey key = (SecretKey)adapter.getItemAtPosition(position);
            Intent intent = new Intent(this, SecretKeyEditActivity.class);
            intent.putExtra("key", key);
            startActivity(intent);
        });

        Button addKey = findViewById(R.id.add_secret_key_button);
        addKey.setOnClickListener((view)-> startActivity(new Intent(this, SecretKeyEditActivity.class)));
        Button backKey = findViewById(R.id.back_to_main_button);
        backKey.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        Button scanQR = findViewById(R.id.qr_scan_button);
        scanQR.setOnClickListener(view -> startActivity(new Intent(this, QrCodeScanner.class)));
    }
}
