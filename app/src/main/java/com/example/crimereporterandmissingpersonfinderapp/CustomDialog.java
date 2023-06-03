package com.example.crimereporterandmissingpersonfinderapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void dismiss() {
        // Do not call the super.dismiss() method to prevent automatic dismissal
        // Instead, add your own logic here if needed
        // For example, you can show a toast message without dismissing the dialog
        Toast.makeText(getContext(), "Custom toast message", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show() {
        super.show();

        Button positiveButton = findViewById(android.R.id.button1);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your desired logic here
                // The dialog will not dismiss automatically

            }
        });
    }
}
