package com.safetys.zatgov;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.safetys.zatgov.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:Created by Ricky on 2017/10/27.
 * Description:
 */
public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.btn_skip)
    Button btnSkip;
    @BindView(R.id.tv_des)
    TextView tvDes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btnSkip.setText("发送事件");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_skip)
    public void onViewClicked() {
        EventBus.getDefault().post(new MessageEvent("来自Second"));
        finish();
    }
}
