package com.ahao.wnacg.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ahao.wnacg.R;

/**
 * Created by Avalon on 2016/5/7.
 */
public class ImagePopup {
    static View popupImageView;
    static PopupWindow popupWindow;
    static ImagePopup imagePopup;
    static ImageView imageView;

    static long start, end;

    private ImagePopup(){}

    public static ImagePopup setParam(Context context){
        if(imagePopup == null){
            imagePopup = new ImagePopup();
        }
        popupImageView = LayoutInflater.from(context).inflate(R.layout.popup_image, null);
        popupWindow = new PopupWindow(popupImageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popup_animation);

        imageView =  (ImageView) popupImageView.findViewById(R.id.image);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("ImagePopup","触摸了");
                popupWindow.dismiss();
                return true;
            }
        });

//        imageView.setEnabled(true);
//        imageView.setFocusable(true);
//        imageView.setFocusableInTouchMode(true);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("ImagePopup","点击了");
//                popupWindow.dismiss();
//            }
//        });
        return imagePopup;
    }

    public void show(View rootView, ImageView cacheImageView) {
        imageView.setImageDrawable(cacheImageView.getDrawable());
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }
}
