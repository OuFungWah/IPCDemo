package com.example.ofw.ipcdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ofw.ipcdemo.mediaplayer.activity.MediaPlayerMainActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button secondBtn;
    private Button thirdBtn;
    private Button mediaPlayerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secondBtn = findViewById(R.id.second_btn);
        thirdBtn = findViewById(R.id.third_btn);
        mediaPlayerBtn = findViewById(R.id.media_main_btn);

        secondBtn.setOnClickListener(this);
        thirdBtn.setOnClickListener(this);
        mediaPlayerBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.second_btn:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.third_btn:
                startActivity(new Intent(this, ThirdActivity.class));
                break;
            case R.id.media_main_btn:
                startActivity(new Intent(this, MediaPlayerMainActivity.class));
                break;
        }
    }
}
