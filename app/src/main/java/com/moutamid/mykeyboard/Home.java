/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moutamid.mykeyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.fxn.stash.Stash;

import java.util.List;


/**
 * Displays the IME preferences inside the input method setting.
 */

public class Home extends Activity {
    boolean isInputDeviceEnabled = false;
    boolean isDefaultKeyboard = false;
    boolean check = false;
    Button btn, active, done, colors;
    LinearLayout colorLayout;
    TextView messageTxt;
    CardView red, green, blue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.settings_name);
        btn = findViewById(R.id.button);
        active = findViewById(R.id.active);
        done = findViewById(R.id.done);
        colors = findViewById(R.id.colors);
        messageTxt = findViewById(R.id.messageTxt);
//        colorLayout = findViewById(R.id.colorLayout);
//        red = findViewById(R.id.red);
//        green = findViewById(R.id.green);
//        blue = findViewById(R.id.blue);

        colors.setOnClickListener(v -> {
            startActivity(new Intent(this, ColorActivity.class));
        });


       // colorLayout.setVisibility(View.GONE);

/*        red.setOnClickListener(v -> {
            Stash.put("color", "red");
            if (check){

                Toast.makeText(this, "Red Theme Applied to Keyboard", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Enable your Keyboard", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please disable your Keyboard First", Toast.LENGTH_SHORT).show();
            }

        });

        green.setOnClickListener(v -> {
            Stash.put("color", "green");
            if (check){

                Toast.makeText(this, "Green Theme Applied to Keyboard", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Enable your Keyboard", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please disable your Keyboard First", Toast.LENGTH_SHORT).show();
            }

        });

        blue.setOnClickListener(v -> {
            Stash.put("color", "blue");
            if (check){

                Toast.makeText(this, "Blue Theme Applied to Keyboard", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Enable your Keyboard", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please disable your Keyboard First", Toast.LENGTH_SHORT).show();
            }
        });*/


        active.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                imeManager.showInputMethodPicker();
            }
        });

        done.setOnClickListener(v -> {
            if (isDefaultKeyboard && isInputDeviceEnabled){
                finish();
                Toast.makeText(this, "Enjoy", Toast.LENGTH_SHORT).show();
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
            if (isInputDeviceEnabled && isDefaultKeyboard) {
                check = false;
                keyboardSetUpDone();
            } else {
                keyboardEnabled();
                check = true;
            }
        }
    }

    private void keyBoardNotEnabled() {
        messageTxt.setVisibility(View.GONE);
        btn.setText(R.string.enable_keyboard);
    }

    private void keyboardEnabled() {
        messageTxt.setVisibility(View.GONE);
        done.setVisibility(View.GONE);

    }

    private void keyboardSetUpDone() {
        messageTxt.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
    }
}
