package com.safetys.zatgov.utils;

import android.app.Activity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Author:Created by Ricky on 2017/11/10.
 * Description:Activity管理器
 */
public class ActivityManager {
    private static HashMap<String, Activity> manager = new HashMap<String, Activity>();

    public static void addActivity(Activity activity) {
        manager.put(activity.getClass().getName(), activity);
    }

    public static Activity getActivity(String activityName) {
        return manager.get(activityName);
    }

    public static void cleanActivities() {
        Collection<Activity> cs = manager.values();
        Iterator<Activity> it = cs.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            activity.finish();
        }
        manager.clear();
    }

    public static void finishActivity(Activity activity) {
        removeActivity(activity);
        activity.finish();
    }

    public static void finishActivity(String activityClassName){
        if (manager.containsKey(activityClassName)) {
            Activity activity = manager.get(activityClassName);
            manager.remove(activityClassName);
            activity.finish();
        }
    }

    public static void removeActivity(Activity activity) {
        String key = activity.getClass().getName();
        if (manager.containsKey(key)) {
            manager.remove(key);
        }
    }
}
