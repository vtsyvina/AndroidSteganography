package edu.gsu.steganography.model;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

public class SecretKeyArrayAdapter extends GenericArrayAdapter<SecretKey> {

    public SecretKeyArrayAdapter(Context context, List<SecretKey> objects){
        super(context, objects);
    }
    @Override
    public void drawText(TextView textView, SecretKey object) {
        textView.setText(object.getName());
    }
}
