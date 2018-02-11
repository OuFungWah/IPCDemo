package com.example.ofw.ipcdemo.mediaplayer.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ofw.ipcdemo.R;

import java.io.IOException;

/**
 * Created by ofw on 2018/2/8.
 */

public class MediaPlayerUriActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener{

    private static final String TAG = "MediaPlayerUriActivity";

    private String url = "http://other.web.rd01.sycdn.kuwo.cn/resource/n3/51/79/3854763359.mp3";

    private Button localPlayBtn;
    private Button localStopBtn;
    private Button netPlayBtn;
    private Button netStopBtn;

    private MediaPlayer localMediaPlayer;
    private MediaPlayer netMediaPlayer;

    private boolean netFlag = false;
    private boolean localFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer_activity_uri);

        localPlayBtn = findViewById(R.id.local_play);
        localStopBtn = findViewById(R.id.local_stop);
        netPlayBtn = findViewById(R.id.network_play);
        netStopBtn = findViewById(R.id.network_stop);


        localPlayBtn.setOnClickListener(this);
        localStopBtn.setOnClickListener(this);
        netPlayBtn.setOnClickListener(this);
        netStopBtn.setOnClickListener(this);

        //解析本地资源的Uri
        Uri localUri = Uri.parse(String.format("android.resource://%s/%s/%s",this.getPackageName(),"raw","always"));
//        Log.d(TAG, "onCreate: " + this.getPackageResourcePath() + "/raw/always");

        localMediaPlayer = new MediaPlayer();
        netMediaPlayer = new MediaPlayer();
        //设置所播放的媒体类型为Music
        localMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        netMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            //设置音频资源
            localMediaPlayer.setDataSource(getApplicationContext(), localUri);
            netMediaPlayer.setDataSource(url);
            //异步准备音乐
            netMediaPlayer.prepareAsync();
            localMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置音乐准备监听器
        netMediaPlayer.setOnPreparedListener(this);
        localMediaPlayer.setOnPreparedListener(this);

        netMediaPlayer.setScreenOnWhilePlaying(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.local_play:
                if (localFlag) {
                    localMediaPlayer.start();
                }
                break;
            case R.id.local_stop:
                localMediaPlayer.pause();
                break;
            case R.id.network_play:
                if (netFlag) {
                    netMediaPlayer.start();
                }
//                netMediaPlayer.start();
                break;
            case R.id.network_stop:
                netMediaPlayer.pause();
                break;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        if(mp==netMediaPlayer){
            netFlag = true;
            Toast.makeText(this, "network music prepared!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onPrepared: network music prepared!!!");
            Log.d(TAG, "onPrepared: Duration:"+netMediaPlayer.getDuration()+"s");

        }
        if(mp==localMediaPlayer){
            localFlag = true;
            Toast.makeText(this, "local music prepared!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onPrepared: local music prepared!!!");
            Log.d(TAG, "onPrepared: Duration:"+localMediaPlayer.getDuration()+"s");
        }

    }

    //释放资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        netMediaPlayer.release();
        netMediaPlayer = null;
        localMediaPlayer.release();
        localMediaPlayer = null;
    }
}
