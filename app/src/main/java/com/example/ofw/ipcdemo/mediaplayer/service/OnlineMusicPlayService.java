package com.example.ofw.ipcdemo.mediaplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by ofw on 2018/2/9.
 */

public class OnlineMusicPlayService extends Service implements MediaPlayer.OnPreparedListener {

    private static final String TAG = "OnlineMusicPlayService";

    public static final int PREPAREING = 0;
    public static final int PREPARED = 1;
    public static final int STARTED = 2;
    public static final int PAUSED = 3;
    public static final int STOPED = 4;
    public static final int COMPLETE = 5;

    private int state = PREPAREING;

    private MyBinder binder = new MyBinder();
    private MediaPlayer mediaPlayer;
    private MusicListener musicListener = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initPlayer(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initPlayer(intent);
        return binder;
    }

    protected void initPlayer(Intent intent) {
        try {
            mediaPlayer.setDataSource(intent.getExtras().getString("Path"));
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放音乐
     */
    public void play() {
        if (state == STARTED || state == PREPARED || state == PAUSED) {
            //当音乐正在播放时再次调用start()时无影响
            mediaPlayer.start();
            state = STARTED;
        }
    }

    /**
     * 暂停音乐
     */
    public void pause() {
        mediaPlayer.pause();
        state = PAUSED;
    }

    /**
     * 暂停音乐
     */
    public void stop() {
        mediaPlayer.stop();
        state = STOPED;
        mediaPlayer.prepareAsync();
    }

    /**
     * 重置
     */
    public void reset() {
        mediaPlayer.reset();
    }

    /**
     * @return 返回歌曲总长
     */
    public int getSongLenght() {
        int duration = 0;
        if (mediaPlayer != null && (state == PREPARED || state == STARTED || state == PAUSED)) {
            duration = mediaPlayer.getDuration();
        }
        return duration;
    }

    public void seekTo(int mesc) {
        mediaPlayer.seekTo(mesc);
    }

    public int getCurrentPosition() {
        int position = 0;
        if (mediaPlayer != null && (state == PREPARED || state == STARTED || state == PAUSED)) {
            position = mediaPlayer.getCurrentPosition();
        }
        return position;
    }

    public double getMediaClockRate() {
        double mediaClockRate = 0;
        if (mediaPlayer != null && (state == PREPARED || state == STARTED || state == PAUSED)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                MediaTimestamp mediaTimestamp = mediaPlayer.getTimestamp();
                mediaTimestamp.getMediaClockRate();
            }
        }
        return mediaClockRate;
    }

    /**
     * @return
     */
    public long getAnchorMediaTimeUs() {
        long time = 0;
        if (mediaPlayer != null && (state == PREPARED || state == STARTED || state == PAUSED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                time = mediaPlayer.getTimestamp().getAnchorSytemNanoTime();
            }
        }
        return time;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //由准备中态进入准备完毕时
        state = PREPARED;
        if (musicListener != null) {
            musicListener.onPrepared(mp);
        }
        //getDuration()方法获取到的时间为ms
        Log.d(TAG, "onPrepared: Duration:" + mediaPlayer.getDuration() + "s");
    }

    public MusicListener getMusicListener() {
        return musicListener;
    }

    public void setMusicListener(MusicListener musicListener) {
        this.musicListener = musicListener;
    }

    /*使用IBinder的对象持有Service对象可以实现把Service对象跨进程通信*/
    public class MyBinder extends Binder {
        public OnlineMusicPlayService getService() {
            return OnlineMusicPlayService.this;
        }

    }

    //回调接口
    public interface MusicListener {
        void onPrepared(MediaPlayer mp);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.release();
        return super.onUnbind(intent);
    }
}
