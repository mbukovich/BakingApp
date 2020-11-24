package com.example.bakingapp.ui.steplistfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Recipe;
import com.example.bakingapp.databinding.RecipeStepItemBinding;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepHolder> {

    private Recipe recipe;

    private OnStepItemClicked onStepItemClicked;

    public interface OnStepItemClicked {
        void onClicked(int index);
    }

    public StepListAdapter(OnStepItemClicked item) {
        onStepItemClicked = item;
    }

    public void setData(Recipe r) {
        recipe = r;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new StepHolder(RecipeStepItemBinding.inflate(inflater));
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder holder, int position) {
        if (position == 0)
            holder.binding.textViewRecipeStep.setText("Ingredients");
        else {
            holder.binding.textViewRecipeStep.setText(recipe.getSteps().get(position-1).getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        if (recipe.getSteps().isEmpty() || recipe.getSteps() == null)
            return 1;
        return recipe.getSteps().size() + 1;
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecipeStepItemBinding binding;

        public StepHolder(RecipeStepItemBinding b) {
            super(b.getRoot());
            binding = b;
            b.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStepItemClicked.onClicked(getAdapterPosition());
        }
    }
}
