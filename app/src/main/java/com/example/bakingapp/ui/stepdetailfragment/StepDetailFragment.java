package com.example.bakingapp.ui.stepdetailfragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.R;
import com.example.bakingapp.Recipe;
import com.example.bakingapp.databinding.FragmentStepDetailsBinding;
import com.example.bakingapp.ui.MasterDetailSharedViewModel;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;

import java.util.List;

public class StepDetailFragment extends Fragment {

    private FragmentStepDetailsBinding binding;

    private MasterDetailSharedViewModel model;

    private SimpleExoPlayer player;

    private Boolean isPhone = true; // default value

    // Necessary constructor
    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentStepDetailsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        player = new SimpleExoPlayer.Builder(getContext()).build();

        model = new ViewModelProvider(requireActivity()).get(MasterDetailSharedViewModel.class);
        model.getCurrentStep().observe(getViewLifecycleOwner(), new Observer<Recipe.Step>() {
            @Override
            public void onChanged(Recipe.Step step) {

                if (step == null) {
                    // this is the ingredients case
                    binding.textViewDetailDescription.setText(model.retrieveIngredients());
                    binding.buttonPreviousStep.setVisibility(View.INVISIBLE);
                    binding.detailsLandscapeView.setVisibility(View.INVISIBLE);
                    binding.detailsPortraitView.setVisibility(View.VISIBLE);
                    binding.playerStepVideo.setVisibility(View.GONE);
                }
                else {
                    // this case handles an actual ingredient step

                    // handle the visibility of the navigation buttons
                    binding.buttonPreviousStep.setVisibility(View.VISIBLE);
                    if (model.isLastStep())
                        binding.buttonNextStep.setVisibility(View.INVISIBLE);
                    else
                        binding.buttonNextStep.setVisibility(View.VISIBLE);

                    // handle filling UI with data
                    binding.textViewDetailDescription.setText(step.getDescription());

                    // Handle Video playback
                    if (!step.getVideoURL().isEmpty()) {
                        if (isPhone) {
                            boolean isLandscape = getResources().getConfiguration().orientation ==
                                    Configuration.ORIENTATION_LANDSCAPE;
                            if (isLandscape) {
                                // set up video for Landscape Mode
                                // binding.detailsPortraitView.setVisibility(View.INVISIBLE);
                                // binding.detailsLandscapeView.setVisibility(View.VISIBLE);
                                // binding.landscapeVideoPlayer.setVisibility(View.VISIBLE);
                                binding.detailsLandscapeView.setVisibility(View.INVISIBLE);
                                binding.detailsPortraitView.setVisibility(View.VISIBLE);
                                binding.playerStepVideo.setVisibility(View.VISIBLE);

                                binding.buttonBar.setVisibility(View.GONE);
                                binding.detailsScrollView.setVisibility(View.GONE);
                            }
                            else {
                                // Set up video in portrait mode
                                binding.detailsLandscapeView.setVisibility(View.INVISIBLE);
                                binding.detailsPortraitView.setVisibility(View.VISIBLE);
                                binding.playerStepVideo.setVisibility(View.VISIBLE);

                                binding.buttonBar.setVisibility(View.VISIBLE);
                                binding.detailsScrollView.setVisibility(View.VISIBLE);
                            }
                            binding.playerStepVideo.setPlayer(player);
                            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(step.getVideoURL()));
                            if (player.getCurrentMediaItem() != mediaItem)
                                player.setMediaItem(mediaItem);
                            player.prepare();
                            player.play();
                        }
                        else {
                            // set up video player for a tablet
                            binding.detailsLandscapeView.setVisibility(View.INVISIBLE);
                            binding.detailsPortraitView.setVisibility(View.VISIBLE);
                            binding.playerStepVideo.setVisibility(View.VISIBLE);
                            binding.playerStepVideo.setPlayer(player);
                            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(step.getVideoURL()));
                            if (player.getCurrentMediaItem() != mediaItem)
                                player.setMediaItem(mediaItem);
                            player.prepare();
                            player.play();
                        }
                    }
                    else {
                        // TODO Hide the Video Player and empty it and make sure we are not in full screen video mode
                        binding.playerStepVideo.setVisibility(View.GONE);
                        binding.detailsLandscapeView.setVisibility(View.INVISIBLE);
                        binding.detailsPortraitView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        // set onClickListeners on the navigation buttons
        binding.buttonNextStep.setOnClickListener(v -> onNextClicked());
        binding.buttonPreviousStep.setOnClickListener(v -> onPreviousClicked());
    }

    public void onPreviousClicked() {
        model.previousStep();
    }

    public void onNextClicked() {
        model.nextStep();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void pauseVideo() {
        if (player != null)
            player.pause();
    }

    public void setPhone(Boolean phone) {
        isPhone = phone;
    }
}
