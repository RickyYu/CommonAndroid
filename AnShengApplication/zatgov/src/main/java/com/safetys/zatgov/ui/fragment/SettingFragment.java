package com.safetys.zatgov.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetys.widget.common.SPUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.service.UpgradeDetector;
import com.safetys.zatgov.ui.activity.ChangePasswordActivity;
import com.safetys.zatgov.utils.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class SettingFragment extends Fragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_setting_photo)
    ImageView imgSettingPhoto;
    @BindView(R.id.text_user_title)
    TextView textUserTitle;
    @BindView(R.id.text_unit)
    TextView textUnit;
    @BindView(R.id.line_x_1)
    View lineX1;
    @BindView(R.id.text_user_name)
    TextView textUserName;
    @BindView(R.id.img_password)
    ImageView imgPassword;
    @BindView(R.id.btn_changepassword)
    Button btnChangepassword;
    @BindView(R.id.img_version)
    ImageView imgVersion;
    @BindView(R.id.text_version)
    TextView textVersion;
    @BindView(R.id.img_new)
    ImageView imgNew;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.img_support)
    ImageView imgSupport;
    @BindView(R.id.img_phone)
    ImageView imgPhone;
    @BindView(R.id.btn_dialing)
    Button btnDialing;
    Unbinder unbinder;
    private String isGrid = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_setting, null);
        unbinder = ButterKnife.bind(this, mView);
        initView(mView);
        return mView;
    }

    private void initView(View mView) {
        if (getActivity().getIntent() != null) {
            isGrid = getActivity().getIntent().getStringExtra("isGrid");
        }
        if (isGrid == null) {
            title.setText("个人中心");
        }
        if ((boolean) SPUtils.getData(PrefKeys.PREF_HAVE_NEW_VERSION, false)) {
            imgNew.setVisibility(View.GONE);
            textVersion.setVisibility(View.GONE);
            btnUpload.setVisibility(View.VISIBLE);
        }
        textUnit.setText("单位:" + (String) SPUtils.getData(PrefKeys.PREF_USER_ORG_NAME_KEY, ""));
        textUserName.setText((String) SPUtils.getData(PrefKeys.PREF_USER_NAME_KEY, ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.text_unit, R.id.btn_changepassword, R.id.btn_upload,  R.id.btn_dialing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_unit:
                DialogUtil.showDefaultAlertDialog_oneb(getActivity(), "所属单位", (String)SPUtils.getData(PrefKeys.PREF_USER_ORG_NAME_KEY, ""), null);
                break;
            case R.id.btn_changepassword:
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_upload:
                UpgradeDetector.processFrontstageOneTime(getActivity());
                break;
            case R.id.btn_dialing:
                Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0574-87364008"));
                startActivity(intentPhone);
                break;
        }
    }
}
