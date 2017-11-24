package com.safetys.zatgov;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.safetys.greenrobot.greendao.gen.UserInfoDao;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.bean.UserInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.entity.MessageEvent;
import com.safetys.zatgov.utils.GreenDaoUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainTestActivity extends AppCompatActivity {
    protected static int TAKE_PICTURE_REQUEST_CODE = 1;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_query)
    Button btnQuery;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    UserInfoDao userInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        ButterKnife.bind(this);
        //注册事件
        EventBus.getDefault().register(this);
        userInfoDao = GreenDaoUtil.getDaoSession().getUserInfoDao();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        ToastUtils.showMessage(getApplicationContext(), "onMessageEvent" + messageEvent.getMessage());
    }

    @OnClick(R.id.btn_skip)
    public void onViewClicked() {
        startActivity(new Intent(MainTestActivity.this, SecondActivity.class));
    }

    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                String companyName = editText.getText().toString();
                String factName = editText2.getText().toString();
                QueryBuilder qb = userInfoDao.queryBuilder();
                ArrayList<UserInfo> list = (ArrayList<UserInfo>) qb.where(UserInfoDao.Properties.UserCompany.eq(companyName)).list();
                if (list.size() > 0) {
                    Toast.makeText(MainTestActivity.this, "已存在该企业名称！", Toast.LENGTH_SHORT).show();
                } else {
                    userInfoDao.insert(new UserInfo(null,companyName,factName,true));
                    Toast.makeText(MainTestActivity.this, "插入数据成功", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_delete:
                File tempFile;// 拍摄缓存照片文件
               // String mImagePath = Environment.getExternalStorageDirectory()+"/meta/";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tempFile = new File(AppConfig.buildPath(AppConfig.HOME_CACHE),
                        UUID.randomUUID().toString().replace("-", "") + ".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
               // tempFile = new File(mImagePath, System.currentTimeMillis()+".jpg");
                if (intent.resolveActivity(getPackageManager()) != null) {
           /*         File path = new File(mImagePath);
                    if(!path.exists()){
                        path.mkdir();
                    }*/
                    try {
                        startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.btn_query:
                String companyName1 = editText.getText().toString();
                QueryBuilder qb1 = userInfoDao.queryBuilder();
                ArrayList<UserInfo> list1 = (ArrayList<UserInfo>) qb1.where(UserInfoDao.Properties.UserCompany.eq(companyName1)).list();
                if (list1.size() > 0) {
                    String text = "";
                    for (UserInfo user : list1) {
                        text = text + "\r\n" + user.getUserCompany();
                    }
                    tvDes.setText(text);
                } else {
                    tvDes.setText("");
                    Toast.makeText(MainTestActivity.this, "不存在该数据", Toast.LENGTH_SHORT).show();
                }

        }
    }
}
