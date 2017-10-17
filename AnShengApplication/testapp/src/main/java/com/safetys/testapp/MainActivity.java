package com.safetys.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.safetys.widget.common.AppUtils;
import com.safetys.widget.common.SPUtils;
import com.safetys.widget.common.ScreenUtils;
import com.safetys.widget.common.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("hello");


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showMessage(getApplicationContext(),"应用名称："+AppUtils.getAppName(mContext)+"     版本号："+AppUtils.getVersionName(mContext));
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ToastUtils.showMessage(getApplicationContext(),"屏幕高度："+ScreenUtils.getScreenHeight(mContext)+"     屏幕宽度："+ScreenUtils.getScreenWidth(mContext));
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ToastUtils.showMessage(mContext,SPUtils.getData("test1","default")+"");
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 int i =  1;
                //保存数据
                SPUtils.saveData("test1","test"+i++);
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存数据
                ToastUtils.showMessage(mContext,SPUtils.getAll()+"");
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存数据
              SPUtils.remove("test1");
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存数据
             SPUtils.clear();
            }
        });




    }
}
