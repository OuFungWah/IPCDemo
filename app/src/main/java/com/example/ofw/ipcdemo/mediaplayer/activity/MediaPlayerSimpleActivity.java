package com.example.ofw.ipcdemo.mediaplayer.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ofw.ipcdemo.R;

/**
 * Created by ofw on 2018/2/8.
 */

public class MediaPlayerSimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer_activity_simple);

        //创建进入准备态的 MediaPlayer 对象，并直接绑定本地资源
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.always);
        //因为是直接使用Create方法，所以无需调用prepare();
        mediaPlayer.start();
    }
}
