package com.example.bakingapp.ui.stepdetailfragment;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
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

    private boolean isPhone = true; // default value

    private boolean isReload;

    // Keys for saving state during lifecycle transitions
    private static final String PLAYER_POSITION_KEY = "playerPosition";
    private static final String PLAY_WHEN_READY_KEY = "playWhenReady";

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

        isReload = savedInstanceState != null;

        model = new ViewModelProvider(requireActivity()).get(MasterDetailSharedViewModel.class);
        model.getCurrentStep().observe(getViewLifecycleOwner(), new Observer<Recipe.Step>() {
            @Override
            public void onChanged(Recipe.Step step) {

                if (step == null) {
                    // this is the ingredients case
                    binding.textViewDetailDescription.setText(model.retrieveIngredients());
                    binding.buttonPreviousStep.setVisibility(View.INVISIBLE);
                    binding.playerStepVideo.setVisibility(View.GONE);
                    binding.stepDetailView.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                    pauseVideo();
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
                                // Make the background for landscape mode black
                                binding.buttonBar.setVisibility(View.GONE);
                                binding.detailsScrollView.setVisibility(View.GONE);
                                binding.stepDetailView.setBackgroundColor(getResources().getColor(R.color.black));
                                if (isReload) {
                                    // getting video to original position during rotation change
                                    if(savedInstanceState.containsKey(PLAYER_POSITION_KEY))
                                        player.seekTo(savedInstanceState.getLong(PLAYER_POSITION_KEY));
                                    player.setPlayWhenReady(savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY));
                                    isReload = false; // this should only be true once
                                }
                            }
                            else {
                                // Set up video in portrait mode
                                binding.buttonBar.setVisibility(View.VISIBLE);
                                binding.detailsScrollView.setVisibility(View.VISIBLE);
                                binding.stepDetailView.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                                if (isReload) {
                                    // getting video to original position during rotation change
                                    if(savedInstanceState.containsKey(PLAYER_POSITION_KEY))
                                        player.seekTo(savedInstanceState.getLong(PLAYER_POSITION_KEY));
                                    player.setPlayWhenReady(savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY));
                                    isReload = false; // this should only be true once
                                }
                            }
                            binding.playerStepVideo.setVisibility(View.VISIBLE);
                            binding.playerStepVideo.setPlayer(player);
                            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(step.getVideoURL()));
                            if (player.getCurrentMediaItem() != mediaItem)
                                player.setMediaItem(mediaItem);
                            player.prepare();
                            player.play();
                        }
                        else {
                            // set up video player for a tablet
                            binding.playerStepVideo.setVisibility(View.VISIBLE);
                            binding.playerStepVideo.setPlayer(player);
                            binding.stepDetailView.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(step.getVideoURL()));
                            if (player.getCurrentMediaItem() != mediaItem)
                                player.setMediaItem(mediaItem);
                            player.prepare();
                            player.play();
                            if (isReload) {
                                // getting video to original position during rotation change
                                if(savedInstanceState.containsKey(PLAYER_POSITION_KEY))
                                    player.seekTo(savedInstanceState.getLong(PLAYER_POSITION_KEY));
                                player.setPlayWhenReady(savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY));
                                isReload = false; // this should only be true once
                            }
                        }
                    }
                    else {
                        // No video URL case
                        binding.playerStepVideo.setVisibility(View.GONE);
                        binding.buttonBar.setVisibility(View.VISIBLE);
                        binding.detailsScrollView.setVisibility(View.VISIBLE);
                        binding.stepDetailView.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                        pauseVideo();
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
        player.release();
    }

    public void pauseVideo() {
        if (player != null)
            player.pause();
    }

    public void setPhone(Boolean phone) {
        isPhone = phone;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION_KEY, player.getContentPosition());
        outState.putBoolean(PLAY_WHEN_READY_KEY, player.getPlayWhenReady());
    }
}
