package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    static List<Recipe> mRecipes;

    public RecipeRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    // this is called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        // TODO get data from network resource
    }

    @Override
    public void onDestroy() {

    }

    // Returns the number of items necessary for the GridView to display
    @Override
    public int getCount() {
        // TODO return the correct number of items for the GridView
        return 0;
    }

    // This very important method is where the data is actually binded to the views.
    // Very similar to onBindViewHolder in a collection Adapter
    @Override
    public RemoteViews getViewAt(int position) {
        // TODO bind data to views
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
