package com.example.botos.appointment.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.botos.appointment.platform.AppointmentApiResponse;

/**
 * Created by Botos on 12/7/2016.
 */
public abstract class ImageUtils {

    public static void setImageToView(final String url, final ImageView imageView) {
        if (Cache.getInstance().getLru().get(url) != null) {
            imageView.setImageBitmap((Bitmap) Cache.getInstance().getLru().get(url));
        } else {
//            Utils.downloadImage(url, new AppointmentApiResponse<Bitmap>() {
//                @Override
//                public void onSuccess(Bitmap response) {
//                    imageView.setImageBitmap(response);
//                }
//
//                @Override
//                public void onFailure() {
//
//                }
//
//                @Override
//                public void onFailure(String error) {
//
//                }
//            });
        }
    }

}
