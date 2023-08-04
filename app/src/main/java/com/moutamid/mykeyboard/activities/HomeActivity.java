package com.moutamid.mykeyboard.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.moutamid.mykeyboard.R;
import com.moutamid.mykeyboard.databinding.ActivityHomeBinding;

import java.util.List;

public class HomeActivity extends Activity {

    ProgressDialog progressDialog;

    private ActivityHomeBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        setTitle(R.string.settings_name);

        b.setColorBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, ColorActivity.class));
        });


        b.done.setOnClickListener(v -> {
        });
    }


}
