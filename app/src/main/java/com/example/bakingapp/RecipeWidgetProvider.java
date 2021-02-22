package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.List;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        // The next several lines of code are for setting up the ListView that displays the selected Recipe's ingredients
        // Create an intent that refers to our "adapter" for the ListView
        Intent listIntent = new Intent(context, ListWidgetService.class);
        listIntent.putExtra(ListWidgetService.WIDGET_ID_KEY, appWidgetId);
        Timber.d("Hooking up the list adapter to the remote gridView");

        // Connect our "adapter" with the GridView
        views.setRemoteAdapter(R.id.lv_widget_ingredients, listIntent);


        // Setting up pending intents for the buttons
        Intent previousIntent = new Intent("com.example.bakingapp.PREVIOUS_RECIPE");
        previousIntent.putExtra(ListWidgetService.WIDGET_ID_KEY, appWidgetId);
        previousIntent.putExtra(RecipeRemoteViewsFactory.RECIPE_INDEX_KEY, -1);
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(context, 0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.bt_widget_previous_recipe, previousPendingIntent);

        Intent nextIntent = new Intent("com.example.bakingapp.NEXT_RECIPE");
        nextIntent.putExtra(ListWidgetService.WIDGET_ID_KEY, appWidgetId);
        nextIntent.putExtra(RecipeRemoteViewsFactory.RECIPE_INDEX_KEY, 1);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.bt_widget_next_recipe, nextPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

