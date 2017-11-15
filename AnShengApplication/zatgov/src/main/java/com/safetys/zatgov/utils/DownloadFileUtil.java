package com.safetys.zatgov.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;

import com.safetys.zatgov.R;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.download.DownloadInfo;
import com.safetys.zatgov.download.DownloadManager;
import com.safetys.zatgov.download.DownloadViewHolder;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;

import java.io.File;
import java.math.BigDecimal;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class DownloadFileUtil {

    private static final int NotificationID = 999888;
    /**
     * 开始下载,异步,断点续传
     * @param context  上下文
     * @param downloadPath  下载路径
     * @param openAfterDownload  设置下载后是否自动打开
     */
    public static void startDownloadFile(final Context context, String downloadPath,
                                         final boolean openAfterDownload) {
        AppConfig.buildPath(AppConfig.HOME_DOWNLOAD);
        String[] rawPath = downloadPath.split("/");
        String name = rawPath[rawPath.length-1];
        //存储位置
        String localPath = getLocalPath(name);
        File localFile = new File(localPath);
        //删除已加载的文件
        if (localFile.exists() && localFile.isFile()) {
            localFile.delete();
        }

        //开始下载
        DownloadManager mDownloadManager = DownloadManager.getInstance();
        DownloadInfo mInfo = new DownloadInfo();
        mInfo.setFileSavePath(localPath);
        mInfo.setUrl(downloadPath);
        mInfo.setLabel(name);
        mInfo.setAutoResume(true);
        mInfo.setAutoRename(false);
        try {
            mDownloadManager.startDownload(mInfo.getUrl(),mInfo.getLabel(),mInfo.getFileSavePath(),
                    mInfo.isAutoResume(), mInfo.isAutoRename(),new MyDownloadViewHolder(context,null,mInfo,true));
        } catch (DbException e) {

            e.printStackTrace();
        }

    }

    private static class MyDownloadViewHolder extends DownloadViewHolder {

        private Notification notification;

        private Context mContext;

        private NotificationManager notificationManager;

        private boolean openAfterDownload;

        private DownloadInfo downloadInfo;

        private RemoteViews notificationView;

        public MyDownloadViewHolder(Context mContext, View view, DownloadInfo downloadInfo, final boolean openAfterDownload) {
            super(view, downloadInfo);
            this.openAfterDownload = openAfterDownload;
            this.downloadInfo = downloadInfo;
            this.mContext = mContext;
            notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        }

        public MyDownloadViewHolder(View view, DownloadInfo downloadInfo) {
            super(view, downloadInfo);

        }

        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {
            String[] rawPath = downloadInfo.getFileSavePath().split("/");
            String name = rawPath[rawPath.length-1];

            //下载文件进度条显示
            notification = new Notification();
            //notification显示
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationView = new RemoteViews(AppConfig.BASE_PACKAGE,
                    R.layout.notification_download_status);
            notificationView.setTextViewText(R.id.notificationTitle, "下载"+name+"中...");
            notificationView.setTextViewText(R.id.notificationPercent, "0%");
            notificationView.setProgressBar(R.id.notificationProgress, 100, 0, false);
            notification.contentView = notificationView;
            notification.tickerText = "下载文件:"+name;
            PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(),
                    0, setIntent(downloadInfo.getFileSavePath()), 0);
            notification.icon = R.drawable.img_download;
            notification.contentIntent = pi;
            notificationManager.notify(NotificationID, notification);
        }

        @Override
        public void onLoading(long count, long current) {
            double percentRaw = (double) current / count;
            // 获取格式化对象
            BigDecimal b = new BigDecimal(percentRaw);
            double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            int percent = (int) (d * 100);
            notificationView.setTextViewText(R.id.notificationPercent, percent + "%");
            notificationView.setProgressBar(R.id.notificationProgress, 100,(int) percent, false);
            notificationManager.notify(NotificationID, notification);
        }

        @Override
        public void onSuccess(File result) {

            notification.tickerText="文件下载完成";
            notificationView.setTextViewText(R.id.notificationTitle, "文件下载完成");
            notificationView.setTextViewText(R.id.notificationPercent, "100%");
            notificationView.setProgressBar(R.id.notificationProgress, 100, 100, false);
            notificationManager.notify(NotificationID, notification);
            if (openAfterDownload) {
                Intent intent = setIntent(downloadInfo.getFileSavePath());
                mContext.startActivity(intent);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
//			//下载失败，设定为不存在
            notification.tickerText = "文件下载失败";
            notificationView.setTextViewText(R.id.notificationTitle, "文件下载失败");
            notificationManager.notify(NotificationID, notification);
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {

        }

    }

    /**
     * 包装Intent
     * @param localPath 文件本地位置
     * @return
     */
    private static Intent setIntent(String localPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(localPath));
        intent.setDataAndType(uri, FileUtil.getMIMEType(localPath));
        return intent;
    }

    private static String getLocalPath(String name){
        return AppConfig.SDCARD_APP_ROOT + File.separator
                + AppConfig.HOME_DOWNLOAD + File.separator + name;
    }
}
