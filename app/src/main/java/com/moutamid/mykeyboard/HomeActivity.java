package com.moutamid.mykeyboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.moutamid.mykeyboard.databinding.ActivityHomeBinding;

import java.util.List;

public class HomeActivity extends Activity {
    boolean isInputDeviceEnabled = false;
    boolean isDefaultKeyboard = false;
    boolean check = false;

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

        b.activateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        });

        b.enableKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                imeManager.showInputMethodPicker();
            }
        });

        b.done.setOnClickListener(v -> {
            if (isDefaultKeyboard && isInputDeviceEnabled) {
                progressDialog.show();
                new Handler().postDelayed(() -> {
                    progressDialog.dismiss();
                    finish();
                    Toast.makeText(this, "Enjoy", Toast.LENGTH_SHORT).show();
                }, 5000);

            } else {
                Toast.makeText(this, "Enable Keyboard First", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //to know when the picker has been closed
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            isInputDeviceEnabled();
    }

    /**
     * function to check weather the soft keyboard has been enabled and set as default by the user
     */
    private void isInputDeviceEnabled() {
        String packageLocal = getPackageName();
        isInputDeviceEnabled = false;
        isDefaultKeyboard = false;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        List<InputMethodInfo> list = inputMethodManager.getEnabledInputMethodList();

        // check if our keyboard is enabled as input method
        for (InputMethodInfo inputMethod : list) {
            String packageName = inputMethod.getPackageName();
            String packageID = inputMethod.getId();

            //checking weather soft keyboard has been enabled
            if (packageName.equals(packageLocal)) {
                isInputDeviceEnabled = true;
            }
            //checking weather soft keyboard has been set as default
            if (packageID.contains(packageLocal) &&
                    inputMethod.getId().equals(Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.DEFAULT_INPUT_METHOD))) {
                isDefaultKeyboard = true;
            }

            /**
             * Update UI
             */
            //progressDialog.show();
            if (isInputDeviceEnabled && isDefaultKeyboard) {
                keyboardSetUpDone();
            } else {
                //progressDialog.dismiss();
                keyboardDisabled();
                check = true;
            }
        }
    }

    private void keyboardDisabled() {
        b.messageTxt.setVisibility(View.GONE);
        b.done.setVisibility(View.GONE);
    }

    private void keyboardSetUpDone() {
        b.messageTxt.setVisibility(View.VISIBLE);
        b.done.setVisibility(View.VISIBLE);
    }
}
