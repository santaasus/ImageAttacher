package com.example.vladimir.imageattacher.attacher;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapDecoder {
    private static final int MAX_READ_LIMIT_PER_IMG = 1024 * 1024;
    private volatile Bitmap bitmaps;

    private Bitmap scaleBitmap(int scaleFactor, InputStream is) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(is, null, bmOptions);
    }

    private int findScaleFactor(int width, int height, InputStream is) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, bmOptions);

        return Math.min(width, height);
    }

    private Bitmap fetchFromUrl(String uri, int width, int height)
            throws IOException {

        if (uri == null) {
            throw new IllegalArgumentException("Wrong uri ");
        }
        URL url = new URL(uri);
        InputStream is = null;

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(httpURLConnection.getInputStream());
            is.mark(MAX_READ_LIMIT_PER_IMG);
            int scaleFactor = findScaleFactor(width, height, is);
            is.reset();

            return scaleBitmap(scaleFactor, is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public void runOnBackgroundFetchFromUrl(final String uri, final int width,
                                            final int height, final Callback callback) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void[] objects) {

                try {
                    bitmaps = fetchFromUrl(uri, width, height);
                } catch (IOException e) {
                    return null;
                }
                return bitmaps;
            }

            // After finally loading we are throwing callback for getting current dates
            @Override
            protected void onPostExecute(Bitmap bitmaps) {
                callback.onFetch(bitmaps);
            }
        }.execute();
    }
}
