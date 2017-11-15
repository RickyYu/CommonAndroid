package com.safetys.zatgov.http;

import com.safetys.zatgov.entity.JsonResult;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
public interface onNetCallback {
    public void onError(String errorMsg);
    public void onSuccess(int requestCode,JsonResult mJsonResult);
}
