/*
 * Copyright (C) 2008-2009 The Android Open Source Project
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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PictureDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodSubtype;

import com.fxn.stash.Stash;

import java.util.List;

public class LatinKeyboardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;
    // TODO: Move this into android.inputmethodservice.Keyboard
    static final int KEYCODE_LANGUAGE_SWITCH = -101;

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
/*
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String color = Stash.getString("color", "red");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.input, null, false);
        LatinKeyboardView keyboardView = view.findViewById(R.id.keyboard);
        LatinKeyboard keyboard = (LatinKeyboard)getKeyboard();
        Log.d("KeyRatio", "color : " + color);
        Log.d("KeyRatio", "view : " + (view == null));
        Log.d("KeyRatio", "keyboardview : " + (keyboardView));

        List<Key> keys = getKeyboard().getKeys();
        for (Key key : keys) {
            if (color.equals("red")) {
                Log.e("KEY", "Drawing key with code " + key.codes[0]);
                Drawable dr = (Drawable ) getResources().getDrawable(R.drawable.red_bg);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);
            } else if (color.equals("blue")) {
                Log.e("KEY", "Drawing key with code " + key.codes[0]);
                Drawable  dr = (Drawable ) getResources().getDrawable(R.drawable.blue_bg);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);

            } else if (color.equals("green")) {

                Log.e("KEY", "Drawing key with code " + key.codes[0]);
                Drawable dr = (Drawable ) getResources().getDrawable(R.drawable.green_bg);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);
            }

            Paint paint = new Paint();
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(48);
            paint.setColor(Color.WHITE);

            if (key.label != null) {
                if (SoftKeyboard.mCapsLock){
                    canvas.drawText(key.label.toString().toUpperCase(), key.x + (key.width / 2),
                            key.y + (key.height / 2), paint);
                } else {
                    canvas.drawText(key.label.toString().toLowerCase(), key.x + (key.width / 2),
                            key.y + (key.height / 2), paint);
                }

            } else {

                //key.icon.setBounds(key.x, key.y, key.x+(key.width/2), key.y+(key.height/2));

                Drawable d = key.icon;
                Bitmap icon;
                if (d instanceof BitmapDrawable) {
                   icon = ((BitmapDrawable)d).getBitmap();
                    int x = (key.x - icon.getWidth())/2;
                    int y = (key.y - icon.getHeight())/2;
                    canvas.drawBitmap(icon, key.x+(key.width/3), key.y+(key.height/3), null);
                }


//                Log.d("KeyRatio" , "key : " + key.toString());
//                Log.d("KeyRatio" , "centerX : " + centreX);
//                Log.d("KeyRatio" , "centreY : " + centreY);
//                Log.d("KeyRatio" , "key.width : " + key.width);
//                Log.d("KeyRatio" , "key.height : " + key.height);
//                Log.d("KeyRatio" , "key.x : " + key.x);
//                Log.d("KeyRatio" , "key.y : " + key.y);


               //key.icon.draw(canvas);
            }

        }
    }

    */

    private Bitmap pictureDrawableToBitmap(PictureDrawable pictureDrawable){
        Bitmap bmp = Bitmap.createBitmap(pictureDrawable.getIntrinsicWidth(), pictureDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawPicture(pictureDrawable.getPicture());
        return bmp;
    }

    @Override
    protected boolean onLongPress(Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }

    void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final LatinKeyboard keyboard = (LatinKeyboard)getKeyboard();
        //keyboard.setSpaceIcon(getResources().getDrawable(subtype.getIconResId()));
        invalidateAllKeys();
    }
}
