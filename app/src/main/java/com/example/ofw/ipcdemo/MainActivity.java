package com.example.ofw.ipcdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button secondBtn;
    private Button thirdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secondBtn = findViewById(R.id.second_btn);
        thirdBtn = findViewById(R.id.third_btn);

        secondBtn.setOnClickListener(this);
        thirdBtn.setOnClickListener(this);

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
        }
    }
}
