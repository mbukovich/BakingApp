package com.example.bakingapp.ui.recipelistfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Recipe;
import com.example.bakingapp.databinding.RecipeLayoutItemBinding;

import java.util.List;

import timber.log.Timber;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    Context context;
    OnRecipeClickedListener onRecipeClickedListener;

    // define an interface to send click events to the fragment/activity
    public interface OnRecipeClickedListener {
        void onClick(int index);
    }

    // we also create a constructor to receive the onRecipeClickedListener
    public RecipeListAdapter(OnRecipeClickedListener listener) { onRecipeClickedListener = listener;}

    public void setData(List<Recipe> data) {
        Timber.d("Setting data for the Recipe List Recycler View");
        recipes = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new RecipeViewHolder(RecipeLayoutItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Timber.d("Binding data to viewHolder: %s", position);
        Timber.d("The name of this recipe is: %s", recipes.get(position).getName());
        holder.binding.textViewRecipeName.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (recipes == null)
            return 0;
        Timber.d("The number of recipes is: %s", recipes.size());
        return recipes.size();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecipeLayoutItemBinding binding;

        public RecipeViewHolder(RecipeLayoutItemBinding b) {
            super(b.getRoot());
            binding = b;
            b.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Timber.d("Click Event in Adapter. Adapter position: %s", getAdapterPosition());
            onRecipeClickedListener.onClick(getAdapterPosition());
        }
    }
}
