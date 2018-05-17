package com.woodman.emptyviewlayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.woodman.emptyviewlayout.EmptyViewLayout;
import com.woodman.emptyviewlayout.OnErrorViewClickListen;

public class MainActivity extends AppCompatActivity {
    private EmptyViewLayout mEmptyViewLayout;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyViewLayout = findViewById(R.id.empty_view);

        mButton1 = findViewById(R.id.test1);
        mButton2 = findViewById(R.id.test2);
        mButton3 = findViewById(R.id.test3);
        mButton4 = findViewById(R.id.test4);
        mButton5 = findViewById(R.id.test5);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyViewLayout.showView(EmptyViewLayout.LOADDING);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyViewLayout.showView(EmptyViewLayout.LOADDING_FAIL);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyViewLayout.showView(EmptyViewLayout.NO_NET);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyViewLayout.showView(EmptyViewLayout.TIME_OUT);
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyViewLayout.showView(EmptyViewLayout.LOADDING_SUCCESS);
            }
        });



        mEmptyViewLayout.setErrorViewClickListen(new OnErrorViewClickListen() {
            @Override
            public void clickImage(int state) {
                Toast.makeText(MainActivity.this,String.valueOf(state),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clickText(int state) {
                Toast.makeText(MainActivity.this,String.valueOf(state),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
