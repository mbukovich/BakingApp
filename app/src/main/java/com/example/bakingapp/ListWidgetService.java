package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.resourceAccess.ClientApi;
import com.example.bakingapp.resourceAccess.RecipeApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import timber.log.Timber;

public class ListWidgetService extends RemoteViewsService {

    public static final String WIDGET_ID_KEY = "widgetID";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int widgetID = 0;
        if (intent.hasExtra(WIDGET_ID_KEY)) {
            widgetID = intent.getIntExtra(WIDGET_ID_KEY, 0);
        }
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), widgetID);
    }
}

