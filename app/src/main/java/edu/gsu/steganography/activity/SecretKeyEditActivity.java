package edu.gsu.steganography.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import edu.gsu.steganography.R;
import edu.gsu.steganography.model.SecretKey;
import edu.gsu.steganography.service.SecretKeyService;
import edu.gsu.steganography.service.ServiceFactory;

public class SecretKeyEditActivity extends AppCompatActivity {

    SecretKeyService keyService = ServiceFactory.getSecretKeyService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sercret_key_edit);

        Intent i = getIntent();
        SecretKey key = (SecretKey)i.getSerializableExtra("key");
        TextView modeText = findViewById(R.id.mode_text);
        Button deleteButton = findViewById(R.id.delete_secret_key_button);
        Button saveButton = findViewById(R.id.save_secret_key_button);
        Button randomizeKeyButton = findViewById(R.id.random_secret_key_button);
        Button shareButton = findViewById(R.id.share_button);

        if (key == null){
            key = new SecretKey(null, null, null);
            modeText.setText("New key");
            deleteButton.setEnabled(false);
        }

        EditText nameEdit = findViewById(R.id.key_name_edit);
        nameEdit.setText(key.getName());
        EditText keyEdit = findViewById(R.id.secret_key_edit);
        keyEdit.setText(key.getKey());

        SecretKey finalKey = key;
        saveButton.setOnClickListener((view)->{
            finalKey.setName(nameEdit.getText().toString());
            finalKey.setKey(keyEdit.getText().toString());
            keyService.insertOrUpdate(finalKey);
            startActivity(new Intent(this, SercetKeyListActivity.class));
        });
        deleteButton.setOnClickListener(view ->{
            keyService.deleteKey(finalKey.getId());
            startActivity(new Intent(this, SercetKeyListActivity.class));
        });
        randomizeKeyButton.setOnClickListener(view -> keyEdit.setText(UUID.randomUUID().toString()));
        shareButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShareKeyActivity.class);
            intent.putExtra("key", finalKey);
            startActivity(intent);
        });
    }
}
