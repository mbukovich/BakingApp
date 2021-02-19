package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.List;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static List<Recipe> recipes;
    static int recipeItem = 0;

    public static final String RECIPE_INDEX_KEY = "recipeIndexKey";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        // The next several lines of code are for setting up the ListView that displays the selected Recipe's ingredients
        // Create an intent that refers to our "adapter" for the ListView
        Intent listIntent = new Intent(context, ListWidgetService.class);
        listIntent.putExtra(RECIPE_INDEX_KEY, recipeItem);
        Timber.d("Hooking up the list adapter to the remote gridView");

        // Connect our "adapter" with the GridView
        views.setRemoteAdapter(R.id.lv_widget_ingredients, listIntent);

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

