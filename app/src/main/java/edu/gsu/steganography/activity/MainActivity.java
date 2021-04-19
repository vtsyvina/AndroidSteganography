package edu.gsu.steganography.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.gsu.steganography.R;
import edu.gsu.steganography.db.SecretKeyDBHelper;
import edu.gsu.steganography.service.ServiceFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceFactory.setDbHelper(new SecretKeyDBHelper(getApplicationContext()));
        setContentView(R.layout.activity_main);

        Button encode = findViewById(R.id.encode_button);
        Button decode = findViewById(R.id.decode_button);
        Button manageKeys = findViewById(R.id.manage_keys_button);

        encode.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Encode.class)));

        decode.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Decode.class)));

        manageKeys.setOnClickListener((view) -> startActivity(new Intent(this, SercetKeyListActivity.class)));

    }

}