package com.safetys.zatgov;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:Created by Ricky on 2017/10/27.
 * Description:测试
 */
public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.btn_skip)
    Button btnSkip;
    @BindView(R.id.tv_des)
    TextView tvDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        btnSkip.setText("发送事件");

    }

    @OnClick(R.id.btn_skip)
    public void onViewClicked() {
        ToastUtils.showMessage(getApplicationContext(),"发送");
       EventBus.getDefault().post(new MessageEvent("来自Second"));
    this.finish();
    }
}
