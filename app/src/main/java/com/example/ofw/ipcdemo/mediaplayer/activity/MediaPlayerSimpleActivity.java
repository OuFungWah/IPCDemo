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

        //注意：如此做法，已进入Activity即播放音乐，并且除了把App关闭没有其他方法停止播放
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.always);
        //因为是直接使用Create方法，所以无需调用prepare();
        mediaPlayer.start();
    }
}
