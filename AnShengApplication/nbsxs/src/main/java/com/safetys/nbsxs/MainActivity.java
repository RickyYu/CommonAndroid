package com.safetys.nbsxs;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.safetys.nbsxs.base.BaseActivity;
import com.safetys.widget.common.PrefKeys;
import com.safetys.widget.common.ToastUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv_test);
        tv.setText(PrefKeys.PREF_AREA_CODE_KEY);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ToastUtils.showMessage(getApplication(),"test");
            }
        });
    }
}
