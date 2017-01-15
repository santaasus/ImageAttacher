package com.example.vladimir.imageattacher.attacher;


import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageAttacher implements BaseAttacher, Callback {

    private static ImageAttacher imageAttacher;
    private BitmapDecoder bitmapDecoder = new BitmapDecoder();
    private ImageView view;

    public static ImageAttacher init() {
        if(imageAttacher == null) {
            imageAttacher = new ImageAttacher();
        }
        return imageAttacher;
    }

    public void load(String url, ImageView mView) {
        view = mView;
        bitmapDecoder.runOnBackgroundFetchFromUrl(url, view.getWidth(), view.getHeight(), this);

    }

    private void localAttach(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @Override
    public void onFetch(Bitmap bitmap) {
        localAttach(bitmap);
    }
}
