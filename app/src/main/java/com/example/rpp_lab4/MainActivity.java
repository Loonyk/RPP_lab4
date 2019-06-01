package com.example.rpp_lab4;

import android.app.DatePickerDialog;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widget = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widget == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        Intent toWidget = new Intent();
        toWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget);
        setResult(RESULT_CANCELED, toWidget);
        setContentView(R.layout.activity_main);
        final Calendar calendar = Calendar.getInstance();
        final MainActivity activity = this;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                Widget.setDate(calendar.getTime());
                Widget.updateAppWidget(activity, AppWidgetManager.getInstance(activity), widget);
                activity.finish();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }
}
