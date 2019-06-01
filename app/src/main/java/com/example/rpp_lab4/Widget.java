package com.example.rpp_lab4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Widget extends AppCompatActivity {

    public static Date date;
    public final static String notify = "MyNotify";


    public Widget() {
        setDate(Calendar.getInstance().getTime());
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int widgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager,widgetId);
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_widget);
//    }

    public static void setDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 9, 0);
        Widget.date = calendar.getTime();
    }

    private static boolean timeToNotify() {
        Calendar calendar = Calendar.getInstance();
        return date.getTime() <= calendar.getTime().getTime();
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        if (timeToNotify()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notify)
                    .setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setContentTitle("Оповещение")
                    .setContentText("Вы выбрали эту дату");

            Notification notification = builder.build();

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,appWidgetId,intent,0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_widget);
        Calendar calendar = Calendar.getInstance();

        long diff = date.getTime() - calendar.getTime().getTime();
        views.setTextViewText(R.id.date, String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
        views.setOnClickPendingIntent(R.id.date, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
