package cinyida.com.car_driver.ui.control;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;

import cinyida.com.car_driver.R;

/**
 * Created by Zheng on 2017/6/9.
 */

public class NotificationControl {

    private static Notification.Builder builder;
    private static NotificationManager notificationManager;


    public static void NotificationLight(Context activity){
            notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            builder = new Notification.Builder(activity);
            Intent intent=new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(R.drawable.jpush_notification_icon);
            builder.setLargeIcon(BitmapFactory.decodeResource(activity.getResources(),     R.drawable.jpush_notification_icon));
            builder.setAutoCancel(true);
            builder.setContentTitle("会儿出租车");
            builder.setContentText("您的乘客已到达上车地点，请速速前往！");
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.build().flags |= Notification.FLAG_AUTO_CANCEL;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            }
            notificationManager.notify(0,builder.build());
    }
}
