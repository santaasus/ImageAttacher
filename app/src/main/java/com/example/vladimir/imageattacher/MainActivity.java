package com.example.vladimir.imageattacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.vladimir.imageattacher.attacher.ImageAttacher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        String url = "http://javasea.ru/uploads/posts/2013-11/1383410113_kartinka-eva.jpg";
        ImageAttacher.init().load(url, imageView);

        Log.d("Image attacher", "Ok");
    }
}
