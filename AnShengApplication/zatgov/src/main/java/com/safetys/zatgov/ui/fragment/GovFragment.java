package com.safetys.zatgov.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class GovFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_infomation, null);
        update();
        return mView;
    }
    public void update() {
        ToastUtils.showMessage(getActivity(),"GovFragment---UPDATE");

    }
}
