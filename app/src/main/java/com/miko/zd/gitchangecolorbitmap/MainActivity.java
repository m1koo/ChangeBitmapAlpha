package com.miko.zd.gitchangecolorbitmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ChangeColorIconWithText firstbitmap;
    private ChangeColorIconWithText secondbitmap;

    private Button firstButton;
    private Button secondButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        firstbitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstbitmap.ChangeColorToggle();
            }
        });
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondbitmap.ChangeToTp();
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondbitmap.ChangeToOp();
            }
        });
    }

    private void initView() {
        firstbitmap= (ChangeColorIconWithText) findViewById(R.id.btm_first);
        secondbitmap= (ChangeColorIconWithText) findViewById(R.id.btm_second);
        firstButton= (Button) findViewById(R.id.bttotp);
        secondButton= (Button) findViewById(R.id.bttoop);


    }
}
