package com.azz.mygifview;

import java.io.InputStream;

import com.ant.liao.GifImageType;
import com.ant.liao.GifView;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("azz", "onCreate");
        MyGifView myGifView = (MyGifView) findViewById(R.id.myGifView);
        
        myGifView.setImageResource(R.drawable.coffee);
        
//        myGifView.setGifImageType(GifImageType.COVER);
//        myGifView.setImageURI(Uri.parse("/mnt/sda/sda1/智能UI测试数据/picture/b.gif"));
//        myGifView.setGifImage("/mnt/sda/sda1/智能UI测试数据/picture/b.gif");       
        
//        GifView yourGifView = (GifView) findViewById(R.id.yourGifView);
//        yourGifView.setGifImageType(GifImageType.COVER);
//        yourGifView.setGifImage("/mnt/sda/sda1/智能UI测试数据/picture/b.gif");
//        yourGifView.setGifImage("/mnt/sda/sda1/多屏互动/1.png");

        Log.d("azz", "onCreate-end");
    }

}
