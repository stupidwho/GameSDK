package me.toufu.appsdklib.platform;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by zhenghu on 15-5-8.
 */
public class TaskHelper {
    private static HandlerThread mTaskThread;
    private static Handler mTaskHandler;

    public static void init() {
        mTaskThread = new HandlerThread("PlatformThread");
        mTaskThread.start();
        mTaskHandler = new Handler(mTaskThread.getLooper());
    }

    public static void runOnTask(Runnable runnable) {
        mTaskHandler.post(runnable);
    }

    public static void finish() {
        mTaskThread.quit();
        mTaskHandler = null;
        mTaskThread = null;
    }
}
