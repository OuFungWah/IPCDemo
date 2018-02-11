package com.example.ofw.ipcdemo.mediaplayer.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ofw.ipcdemo.R;
import com.example.ofw.ipcdemo.mediaplayer.service.OnlineMusicPlayService;
import com.example.ofw.ipcdemo.tools.TimeFormatTools;

/**
 * Created by ofw on 2018/2/10.
 */

public class MediaPlayerServiceActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, OnlineMusicPlayService.MusicListener {

    private static final String TAG = "MPServiceActivity";

    private TextView totalTimeTv;
    private TextView nowTimeTv;
    private SeekBar timeSeek;
    private Button playBtn;
    private Button pauseBtn;
    private Button stopBtn;
    private OnlineMusicPlayService onlineMusicPlayService;

    private String totalTime;
    private String nowTime;

    private boolean isChanging = false;
    private boolean isPreparing = true;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            onlineMusicPlayService = ((OnlineMusicPlayService.MyBinder) service).getService();
            onlineMusicPlayService.setMusicListener(MediaPlayerServiceActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            onlineMusicPlayService = null;
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (!isChanging) {
                        handler.sendEmptyMessage(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Thread thread = new Thread(runnable);

    int progress = 0;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            progress = onlineMusicPlayService.getCurrentPosition();
            nowTime = TimeFormatTools.ms2MS(progress);
            nowTimeTv.setText(nowTime);
            Log.d(TAG, "handleMessage: progress = " + progress);
            timeSeek.setProgress(progress);
            return false;
        }
    });

    private String url = "http://other.web.nf01.sycdn.kuwo.cn/resource/n1/40/87/2977892568.mp3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer_activity_service);

        Intent intent = new Intent(this, OnlineMusicPlayService.class);
        Bundle bundle = new Bundle();
        bundle.putString("Path", url);
        intent.putExtras(bundle);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        totalTimeTv = findViewById(R.id.total_time_tv);
        nowTimeTv = findViewById(R.id.now_time_tv);
        playBtn = findViewById(R.id.play_btn);
        pauseBtn = findViewById(R.id.pause_btn);
        stopBtn = findViewById(R.id.stop_btn);
        timeSeek = findViewById(R.id.music_seekbar);

        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        timeSeek.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        if (!isPreparing) {
            switch (v.getId()) {
                case R.id.play_btn:
                    if (!thread.isAlive()) {
                        thread.start();
                    }
                    onlineMusicPlayService.play();
                    break;
                case R.id.pause_btn:
                    onlineMusicPlayService.pause();
                    break;
                case R.id.stop_btn:
                    isPreparing = true;
                    progress = 0;
                    onlineMusicPlayService.seekTo(0);
                    timeSeek.setProgress(progress);
                    onlineMusicPlayService.stop();
                    break;
            }
        }
    }

    //SeekBar回掉接口

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            onlineMusicPlayService.seekTo(progress);
            nowTime = TimeFormatTools.ms2MS(progress);
            nowTimeTv.setText(nowTime);
            Log.d(TAG, "onProgressChanged: user progress = " + progress);
        } else {
            Log.d(TAG, "onProgressChanged: auto progress = " + progress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isChanging = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isChanging = false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        isPreparing = false;
        timeSeek.setMax(mp.getDuration());
        totalTime = TimeFormatTools.ms2MS(onlineMusicPlayService.getSongLenght());
        totalTimeTv.setText(totalTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        thread = null;
    }
}
