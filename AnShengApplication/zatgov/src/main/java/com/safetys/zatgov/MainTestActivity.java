package com.safetys.zatgov;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainTestActivity extends AppCompatActivity {
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.btn_skip)
    Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        ToastUtils.showMessage(getApplicationContext(),"onMessageEvent"+messageEvent.getMessage());
    }

    @OnClick(R.id.btn_skip)
    public void onViewClicked() {
        startActivity(new Intent(MainTestActivity.this,SecondActivity.class));
    }
}
