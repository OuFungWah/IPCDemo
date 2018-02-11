package com.example.ofw.ipcdemo.mediaplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ofw.ipcdemo.R;

/**
 * Created by ofw on 2018/2/8.
 */

public class MediaPlayerMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button simpleButton;
    private Button uriButton;
    private Button serviceButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer_activity_main);
        simpleButton = findViewById(R.id.media_player_simple_btn);
        uriButton = findViewById(R.id.media_player_uri_btn);
        serviceButton = findViewById(R.id.media_player_service_btn);

        simpleButton.setOnClickListener(this);
        uriButton.setOnClickListener(this);
        serviceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.media_player_simple_btn:
                startActivity(new Intent(this,MediaPlayerSimpleActivity.class));
                break;
                case R.id.media_player_uri_btn:
                startActivity(new Intent(this,MediaPlayerUriActivity.class));
                break;
            case R.id.media_player_service_btn:
                startActivity(new Intent(this,MediaPlayerServiceActivity.class));
                break;
        }
    }
}
