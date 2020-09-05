package com.ftouchcustomer.ImageEdit;

import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by sangc on 2015-11-06.
 */
public class ImageController {
    ImageView imgMain;

    public ImageController(ImageView imgMain) {
        this.imgMain = imgMain;
    }

    public void setImgMain(Uri path) {
        Picasso.get().load(path).fit().centerCrop().into(imgMain);
    }

    public void setImgByURL(String imagePaths) {
        Picasso.get().load(imagePaths).fit().centerCrop().into(imgMain);
    }

}
